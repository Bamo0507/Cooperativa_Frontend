package app.cooperativa.domain.socios

import app.cooperativa.data.localdb.socios.SPrestamoMockData
import app.cooperativa.data.model.dto.Prestamo

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