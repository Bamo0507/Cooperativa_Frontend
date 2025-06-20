package app.cooperativa.domain.directiva

import app.cooperativa.data.localdb.directiva.PaymentMockData
import app.cooperativa.data.model.dto.Payment

interface DPaymentsDetailRepository {
    suspend fun getPaymentById(id: Int): Payment
}

class MockPaymentsDetailRepository : DPaymentsDetailRepository {
    override suspend fun getPaymentById(id: Int): Payment = PaymentMockData.getPaymentById(id)!!
}