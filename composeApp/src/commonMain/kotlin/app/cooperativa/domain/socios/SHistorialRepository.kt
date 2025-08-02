package app.cooperativa.domain.socios

import app.cooperativa.data.model.dto.HistoryResponse
import app.cooperativa.data.model.dto.Prestamo
import app.cooperativa.graphql.GetHistoryQuery
import app.cooperativa.graphql.GraphQLClientProvider
import com.apollographql.apollo3.api.ApolloResponse

interface SHistorialRepository {
    suspend fun getPrestamosByUser(accessToken: String): List<Prestamo>
    suspend fun fetchHistory(accessToken: String): HistoryResponse
}

// ---------------------------------------------------------------------
class SociosHistorialRepository(
    private val clientProvider: GraphQLClientProvider
) : SHistorialRepository {
    //TODO: Implementar en el client cuando lo tengan
    override suspend fun getPrestamosByUser(accessToken: String): List<Prestamo> {
        return emptyList()
    }

    override suspend fun fetchHistory(accessToken: String): HistoryResponse {
        val response: ApolloResponse<GetHistoryQuery.Data> = clientProvider.getHistoryResponse(accessToken)
        val history = response.data?.getHistory
            ?: throw Exception("Ooops, no se pudo obtener el historial!")

        return HistoryResponse(
            owedCapital = history.owedCapital?.toFloat() ?: 1f,
            payedToCapital = history.payedToCapital?.toFloat() ?: 1f
        )
    }
}