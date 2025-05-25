package app.cooperativa.domain.directiva

import app.cooperativa.data.localdb.PaymentMockData
import app.cooperativa.data.model.dto.Payment

interface DPendingPayRepository {
    suspend fun getPaymentById(id: Int): Payment?
}

class MockPendingPayRepository : DPendingPayRepository {
    override suspend fun getPaymentById(id: Int) = PaymentMockData.getPaymentById(id)
}