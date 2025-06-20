package app.cooperativa.domain.socios

import app.cooperativa.data.localdb.socios.SPrestamoStatusMockData
import app.cooperativa.data.model.dto.PagaresStatus
import app.cooperativa.data.model.dto.PrestamosStatus

interface SPrestamoRepository {
    suspend fun getPrestamosSolicitudesByUser(userId: Int): List<PrestamosStatus>
    suspend fun getPagaresSolicitudesByUser(userId: Int): List<PagaresStatus>
}

class MockSociosPrestamoRepository : SPrestamoRepository {
    override suspend fun getPrestamosSolicitudesByUser(userId: Int): List<PrestamosStatus> {
        return SPrestamoStatusMockData.getPrestamosStatus(userId)
    }

    override suspend fun getPagaresSolicitudesByUser(userId: Int): List<PagaresStatus> {
        return SPrestamoStatusMockData.getPagaresStatus(userId)
    }
}