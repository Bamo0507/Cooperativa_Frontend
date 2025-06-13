package app.cooperativa.domain.directiva

import app.cooperativa.data.localdb.PagaresMockData
import app.cooperativa.data.model.dto.Pagare

interface DPagaresRepository {
    suspend fun fetchPagareById(pagareId: Int): Pagare
}

class MockPagaresRepository : DPagaresRepository {
    override suspend fun fetchPagareById(pagareId: Int): Pagare = PagaresMockData.getPagareById(pagareId)!!
}