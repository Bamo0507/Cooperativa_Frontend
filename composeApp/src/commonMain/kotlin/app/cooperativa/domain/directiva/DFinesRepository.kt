package app.cooperativa.domain.directiva

import app.cooperativa.data.localdb.directiva.FineMockData
import app.cooperativa.data.model.dto.Fine

interface DFinesRepository {
    suspend fun getFinesForUserById(id: Int): Fine
}

class MockFinesRepository : DFinesRepository {
    override suspend fun getFinesForUserById(id: Int): Fine = FineMockData.getFinesByUser(id)!!
}
