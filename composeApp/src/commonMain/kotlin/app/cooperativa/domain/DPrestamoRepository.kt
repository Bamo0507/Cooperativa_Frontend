package app.cooperativa.domain

import app.cooperativa.data.localdb.PrestamoMockData
import app.cooperativa.data.localdb.SolicitudPrestamoMockData
import app.cooperativa.data.model.dto.Prestamo
import app.cooperativa.data.model.ui.BasicInfoLoan

//Interfaz para obtener datos de prestamos y solicitudes de estos
interface DPrestamoRepository {
    suspend fun fetchSolicitudes(): List<BasicInfoLoan>
    suspend fun fetchPrestamosAprobados(): List<Prestamo>
}

// Implementacion del repositorio
// De momento solo manejamos la mock data
class MockPrestamosRepository : DPrestamoRepository {
    override suspend fun fetchSolicitudes(): List<BasicInfoLoan> =
        SolicitudPrestamoMockData.getAllBasicInfo()

    override suspend fun fetchPrestamosAprobados(): List<Prestamo> =
        PrestamoMockData.getAllPrestamos()
}