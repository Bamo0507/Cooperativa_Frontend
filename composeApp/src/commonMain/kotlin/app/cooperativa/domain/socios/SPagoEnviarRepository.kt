package app.cooperativa.domain.socios

import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.graphql.type.PayedTo
import app.cooperativa.data.model.dto.QuotaAffiliate
import app.cooperativa.graphql.CreateUserPaymentMutation
import app.cooperativa.graphql.GetFinesByIdQuery
import app.cooperativa.graphql.GetMonthlyAffiliateQuotaQuery
import app.cooperativa.graphql.GetPendingLoansQuotasQuery
import app.cooperativa.graphql.type.FineStatus
import com.apollographql.apollo3.ApolloClient

interface SPagoEnviarRepository {
    suspend fun getMonthlyAffiliateQuota(accessToken: String): List<QuotaAffiliate>
    suspend fun getPendingLoansQuotas(accessToken: String): List<LoanQuota>
    suspend fun getFinesByAccessToken(accessToken: String): List<FinePayAffiliate>
    suspend fun createUserPayment(
        accessToken: String,
        name: String,
        totalAmount: Float,
        ticketNumber: String,
        accountNumber: String,
        beingPayed: List<PayedTo>,
    ): String
}

class SociosPagoEnviarRepository(
    private val fineApollo: ApolloClient, // /graphql/fine
    private val quotaApollo: ApolloClient, // /graphql/quota
    private val paymentApollo: ApolloClient // /graphql/payment
) : SPagoEnviarRepository {
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
                idCuota = q.userId, // Segun comentarios debe coincidir con affiliate key
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
        name: String,
        totalAmount: Float,
        ticketNumber: String,
        accountNumber: String,
        beingPayed: List<PayedTo>,
    ): String {
        val resp = paymentApollo.mutation(
            CreateUserPaymentMutation(
                accessToken = accessToken,
                name = name,
                totalAmount = totalAmount.toDouble(), // Apollo usa Double en scalars Float
                ticketNumber = ticketNumber,
                accountNumber = accountNumber,
                beingPayed = beingPayed,
            )
        ).execute()

        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (createUserPayment)" })
        }

        // la mutación devuelve String!, con alias "response"
        return resp.data?.response ?: throw RuntimeException("Respuesta vacía (createUserPayment)")
    }
}
