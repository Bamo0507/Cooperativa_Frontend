package app.cooperativa.domain.socios

import app.cooperativa.core.network.apollo.GraphQlException
import app.cooperativa.core.network.apollo.executeQuery
import app.cooperativa.data.model.dto.HistoryResponse
import app.cooperativa.data.model.dto.Prestamo
import app.cooperativa.graphql.GetHistoryQuery
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse

interface SHistorialRepository {
    suspend fun getPrestamosByUser(accessToken: String): List<Prestamo>
    suspend fun fetchHistory(accessToken: String): HistoryResponse
}

// ---------------------------------------------------------------------
class SociosHistorialRepository(
    private val apollo: ApolloClient
) : SHistorialRepository {

    // TODO: implementar cuando exista el query en backend
    override suspend fun getPrestamosByUser(accessToken: String): List<Prestamo> {
        return emptyList()
    }

    override suspend fun fetchHistory(accessToken: String): HistoryResponse {
        return apollo.executeQuery(GetHistoryQuery(accessToken = accessToken)) { data ->
            val h = data.getHistory
                ?: throw GraphQlException("Ooops, no se pudo obtener el historial")

            HistoryResponse(
                owedCapital = h.owedCapital?.toFloat() ?: 0f,
                payedToCapital = h.payedToCapital?.toFloat() ?: 0f
            )
        }
    }
}