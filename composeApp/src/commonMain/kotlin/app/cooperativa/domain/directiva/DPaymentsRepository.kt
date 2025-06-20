package app.cooperativa.domain.directiva

import app.cooperativa.data.localdb.directiva.FineMockData
import app.cooperativa.data.localdb.directiva.PaymentMockData
import app.cooperativa.data.model.dto.Fine
import app.cooperativa.data.model.ui.BasicInfoPayment

interface DPaymentsRepository {
    suspend fun getAllPaymentsBasicInfo(): List<BasicInfoPayment>
    suspend fun getAllFines(): List<Fine>
}

class MockPaymentsRepository : DPaymentsRepository {
    override suspend fun getAllPaymentsBasicInfo(): List<BasicInfoPayment> = PaymentMockData.getAllPaymentsBasicInfo()
    override suspend fun getAllFines(): List<Fine> = FineMockData.getAllFines()
}
