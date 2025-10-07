package app.cooperativa.domain.socios

import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.QuotaAffiliate
import app.cooperativa.graphql.GetFinesByIdQuery
import app.cooperativa.graphql.GetMonthlyAffiliateQuotaQuery
import app.cooperativa.graphql.GetPendingLoansQuotasQuery
import com.apollographql.apollo3.ApolloClient

interface SPagoEnviarRepository {
    suspend fun getMonthlyAffiliateQuota(accessToken: String): List<QuotaAffiliate>
    suspend fun getPendingLoansQuotas(accessToken: String): List<LoanQuota>
    suspend fun getFinesByAccessToken(accessToken: String): List<FinePayAffiliate>
}

class SociosPagoEnviarRepository(
    private val fineApollo: ApolloClient, // /graphql/fine
    private val quotaApollo: ApolloClient // /graphql/quota
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

        return fines.map { f ->
            FinePayAffiliate(
                fineName = f.reason,
                fineAmount = f.quantity.toFloat()
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

        // Mapeo a tu DTO actual (usamos el identifier como “nombreAsociado” y un id sintético)
        return items.map { n ->
            QuotaAffiliate(
                idCuota = n.identifier.hashCode(),
                idAsociado = n.userId,
                identifier = n.identifier,
                montoCuota = n.monto.toFloat()
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

        return items.map { n ->
            val display = n.nombrePrestamo ?: "Préstamo"
            val label = "$display - cuota ${n.numeroQuota}"
            LoanQuota(
                id = n.loanId,
                nombrePago = label,
                monto = n.monto.toFloat()
            )
        }
    }
}
