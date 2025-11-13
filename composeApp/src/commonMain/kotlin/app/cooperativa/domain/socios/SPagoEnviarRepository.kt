package app.cooperativa.domain.socios

import app.cooperativa.core.network.ktor.dto.UploadTicketResponse
import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.graphql.type.PayedToInput
import app.cooperativa.data.model.dto.QuotaAffiliate
import app.cooperativa.graphql.CreateUserPaymentMutation
import app.cooperativa.graphql.GetFinesByIdQuery
import app.cooperativa.graphql.GetMonthlyAffiliateQuotaQuery
import app.cooperativa.graphql.GetPendingLoansQuotasQuery
import app.cooperativa.graphql.type.FineStatus
import com.apollographql.apollo3.ApolloClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess

interface SPagoEnviarRepository {
    suspend fun getMonthlyAffiliateQuota(accessToken: String): List<QuotaAffiliate>
    suspend fun getPendingLoansQuotas(accessToken: String): List<LoanQuota>
    suspend fun getFinesByAccessToken(accessToken: String): List<FinePayAffiliate>
    suspend fun uploadTicket(accessToken: String, jpegBytes: ByteArray): String
    suspend fun createUserPayment(
        accessToken: String,
        comprobantePath: String,
        name: String,
        totalAmount: Float,
        ticketNumber: String,
        accountNumber: String,
        beingPayed: List<PayedToInput>,
    ): String
}

class SociosPagoEnviarRepository(
    private val fineApollo: ApolloClient, // /graphql/fine
    private val quotaApollo: ApolloClient, // /graphql/quota
    private val paymentApollo: ApolloClient, // /graphql/payment
    private val http: HttpClient,
) : SPagoEnviarRepository {
    override suspend fun uploadTicket(accessToken: String, jpegBytes: ByteArray): String {
        val url = "https://dev.cooperativa-isp.cc/general/upload_ticket_payment"

        val resp = http.post(url) {
            url { parameters.append("access_token", accessToken) }

            // NO pongas contentType a nivel de request. Deja que multipart lo establezca.
            setBody(
                MultiPartFormDataContent(
                    formData {
                        // Nombre de campo típico: "file" (ajústalo si tu backend usa otro nombre)
                        append(
                            key = "file",
                            value = jpegBytes,
                            headers = Headers.build {
                                append(
                                    HttpHeaders.ContentDisposition,
                                    """form-data; name="file"; filename="ticket.jpeg""""
                                )
                                append(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
                            }
                        )
                    }
                )
            )
        }

        if (!resp.status.isSuccess()) {
            throw RuntimeException("Upload ticket failed: ${resp.status.value}")
        }

        val dto = resp.body<UploadTicketResponse>()
        val ticket = dto.ok?.ticketId
        return ticket ?: throw RuntimeException("Respuesta inválida del upload (sin ticket_id)")
    }

    override suspend fun getFinesByAccessToken(accessToken: String): List<FinePayAffiliate> {
        val response = fineApollo.query(
            GetFinesByIdQuery(accessToken = accessToken)
        ).execute()

        if (response.hasErrors()) {
            val msg = response.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (getFinesById)" })
        }

        val fines = response.data?.getFinesById
            ?: throw RuntimeException("Respuesta vacía (getFinesById)")

        // Filtra solo UNPAID y mapea al DTO con id + amount
        return fines
            .filter { it.status == FineStatus.UNPAID }
            .map { f ->
                FinePayAffiliate(
                    id = f.id,
                    fineName = f.reason,
                    fineAmount = f.amount.toFloat()
                )
            }
    }

    // --- CUOTAS MENSUALES ---
    override suspend fun getMonthlyAffiliateQuota(accessToken: String): List<QuotaAffiliate> {
        val response = quotaApollo.query(
            GetMonthlyAffiliateQuotaQuery(accessToken)
        ).execute()

        if (response.hasErrors()) {
            val msg = response.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (getMonthlyAffiliateQuota)" })
        }

        val items = response.data?.getMonthlyAffiliateQuota
            ?: throw RuntimeException("Respuesta vacía (getMonthlyAffiliateQuota)")

        // Solo cuotas no pagadas y de tipo Afiliado
        val pendientes = items.filter { it.payed != true && it.quotaType.rawValue == "AFILIADO" }

        return pendientes.map { q ->
            QuotaAffiliate(
                idCuota = q.userId,
                idAsociado = q.userId,
                identifier = q.identifier ?: (q.nombreUsuario ?: "Afiliado"),
                montoCuota = q.amount.toFloat()
            )
        }
    }

    // --- CUOTAS DE PRÉSTAMO ---
    override suspend fun getPendingLoansQuotas(accessToken: String): List<LoanQuota> {
        val response = quotaApollo.query(
            GetPendingLoansQuotasQuery(accessToken)
        ).execute()

        if (response.hasErrors()) {
            val msg = response.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (getPendingLoansQuotas)" })
        }

        val items = response.data?.getPendingLoansQuotas
            ?: throw RuntimeException("Respuesta vacía (getPendingLoansQuotas)")

        // Solo cuotas no pagadas y de tipo Prestamo
        val pendientes = items.filter { it.payed != true && it.quotaType.rawValue == "PRESTAMO" }

        return pendientes.map { q ->
            val display = q.nombrePrestamo ?: "Préstamo"
            val label = if (q.quotaNumber != null) "$display - cuota ${q.quotaNumber}" else display
            LoanQuota(
                id = q.loanId ?: (q.identifier ?: display),
                nombrePago = label,
                monto = q.amount.toFloat()
            )
        }
    }

    // MUTATION 2 SEND PAYMENT
    override suspend fun createUserPayment(
        accessToken: String,
        comprobantePath: String,
        name: String,
        totalAmount: Float,
        ticketNumber: String,
        accountNumber: String,
        beingPayed: List<PayedToInput>,
    ): String {
        val resp = paymentApollo.mutation(
            CreateUserPaymentMutation(
                accessToken = accessToken,
                comprobantePath = comprobantePath,
                name = name,
                totalAmount = totalAmount.toDouble(),
                ticketNumber = ticketNumber,
                accountNumber = accountNumber,
                beingPayed = beingPayed,
            )
        ).execute()

        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (createUserPayment)" })
        }
        return resp.data?.response ?: throw RuntimeException("Respuesta vacía (createUserPayment)")
    }
}
