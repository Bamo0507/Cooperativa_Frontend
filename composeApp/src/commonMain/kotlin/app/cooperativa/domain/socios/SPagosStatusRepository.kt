package app.cooperativa.domain.socios

import app.cooperativa.data.localdb.socios.SPagoStatusMockData
import app.cooperativa.data.model.dto.PagosStatus

interface SPagosStatusRepository {
    suspend fun getPagoStatusByUser(userId: Int): List<PagosStatus>
}

//Mock impelmentation of the repository
class MockSociosPagosStatusRepository: SPagosStatusRepository {
    override suspend fun getPagoStatusByUser(userId: Int): List<PagosStatus> {
        return SPagoStatusMockData.getPagosStatusByUser(userId)
    }
}