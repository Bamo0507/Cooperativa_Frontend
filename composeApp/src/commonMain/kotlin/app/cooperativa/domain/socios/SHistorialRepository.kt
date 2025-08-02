package app.cooperativa.domain.socios

import app.cooperativa.data.localdb.socios.SPrestamoMockData
import app.cooperativa.data.model.dto.Prestamo
import app.cooperativa.graphql.GetHistoryQuery
import app.cooperativa.graphql.GraphQLClientProvider
import com.apollographql.apollo3.api.ApolloResponse

interface SHistorialRepository {
    suspend fun getPrestamosByUser(
        userId: Int
    ): List<Prestamo>

    suspend fun getTotalAportesByUser(userId: Int): Float

    suspend fun getTotalCapitalPorPagar(userId: Int): Float
}

class MockSociosHistorialRepository : SHistorialRepository {
    override suspend fun getPrestamosByUser(userId: Int): List<Prestamo> =
        SPrestamoMockData.getPrestamosByUser(userId)

    override suspend fun getTotalAportesByUser(userId: Int): Float = 1000000.0f

    override suspend fun getTotalCapitalPorPagar(userId: Int): Float = 10005.0f
}

class SociosHistorialRepository (
    private val client: GraphQLClientProvider
): SHistorialRepository {
    private suspend fun fetchHistory(): GetHistoryQuery.GetHistory {
        val response: ApolloResponse<GetHistoryQuery.Data> = client.getHistoryResponse()
        val history = response.data?.getHistory
            ?: throw Exception("Respuesta vacía de getHistory") // puedes mapear errores más finos
        return history
    }

    // TODO: IMPLEMENTAR CUANDO TENGAN ESTE GRAPHQL QUERY
    override suspend fun getPrestamosByUser(userId: Int): List<Prestamo> {
        // Por ahora la API que mostraste no devuelve préstamos. Dejar vacío o implementar nueva query cuando esté disponible.
        return emptyList()
    }

    override suspend fun getTotalAportesByUser(userId: Int): Float {
        return fetchHistory().payedToCapital?.toFloat() ?: 1.0f
    }

    override suspend fun getTotalCapitalPorPagar(userId: Int): Float {
        return fetchHistory().owedCapital?.toFloat() ?: 1.0f
    }

}