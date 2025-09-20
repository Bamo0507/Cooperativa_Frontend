package app.cooperativa.domain.socios

import app.cooperativa.data.localdb.socios.SPagoEnviarMockData
import app.cooperativa.data.model.dto.BasicUserInfo
import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.QuotaAffiliate
import app.cooperativa.graphql.GetFinesByIdQuery
import com.apollographql.apollo3.ApolloClient

interface SPagoEnviarRepository {
    suspend fun getCuotasMensualesPendientes(): List<QuotaAffiliate>
    suspend fun getPrestamoCuotasByUser(userId: Int): List<LoanQuota>
    suspend fun getFinesByAccessToken(accessToken: String): List<FinePayAffiliate>

    suspend fun getAllUsers(): List<BasicUserInfo>
}

class SociosPagoEnviarRepository(
    private val fineApollo: ApolloClient
) : SPagoEnviarRepository {
    //TODO: penditne de definir x backend
    override suspend fun getCuotasMensualesPendientes(): List<QuotaAffiliate> = emptyList()
    override suspend fun getPrestamoCuotasByUser(userId: Int): List<LoanQuota> = emptyList()
    override suspend fun getAllUsers(): List<BasicUserInfo> = emptyList()

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
}
