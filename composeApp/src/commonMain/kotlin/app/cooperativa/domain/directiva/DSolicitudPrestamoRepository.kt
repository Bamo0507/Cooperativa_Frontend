package app.cooperativa.domain.directiva

import app.cooperativa.data.localdb.SolicitudPrestamoMockData
import app.cooperativa.data.model.dto.SolicitudPrestamo

interface DSolicitudPrestamoRepository {
    suspend fun getSolicitudById(id: Int): SolicitudPrestamo?
}

class MockSolicitudPrestamoRepository: DSolicitudPrestamoRepository {
    override suspend fun getSolicitudById(id: Int): SolicitudPrestamo? =
        SolicitudPrestamoMockData.getSolicitudById(id)
}