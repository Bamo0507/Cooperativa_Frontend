package app.cooperativa.domain.socios

import app.cooperativa.data.localdb.socios.SPagoEnviarMockData
import app.cooperativa.data.model.dto.BasicUserInfo
import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.QuotaAffiliate

interface SPagoEnviarRepository {
    suspend fun getCuotasMensualesPendientes(): List<QuotaAffiliate>
    suspend fun getPrestamoCuotasByUser(userId: Int): List<LoanQuota>
    suspend fun getPagoMultasByQuotasUser(usersIds: List<Int>): List<FinePayAffiliate>
    suspend fun getAllUsers(): List<BasicUserInfo>
}

// Mock implementation of the repository
class MockSociosPagoEnviarRepository: SPagoEnviarRepository {
    override suspend fun getCuotasMensualesPendientes(): List<QuotaAffiliate> =
        SPagoEnviarMockData.getCuotasMensualesPendientes()

    override suspend fun getPrestamoCuotasByUser(userId: Int): List<LoanQuota> =
        SPagoEnviarMockData.getPrestamoCuotasByUser(userId)

    override suspend fun getPagoMultasByQuotasUser(usersIds: List<Int>): List<FinePayAffiliate> =
        SPagoEnviarMockData.getPagoMultasByQuotasUser(usersIds)

    override suspend fun getAllUsers(): List<BasicUserInfo> =
        SPagoEnviarMockData.getAllUsers()
}