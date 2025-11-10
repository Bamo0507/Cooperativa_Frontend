package app.cooperativa.data.localdb.socios

import app.cooperativa.data.model.dto.Loan
import app.cooperativa.data.model.dto.LoanUiStatus

object SPrestamoMockData {
    private val mockLoans = listOf(
        Loan(
            id = "1",
            presentedByName = "Laura Martínez",
            reason = "Compra Casa",
            total = 10_000.0f,
            payed = 10_000.0f,
            debt = 0.0f,
            interestRate = 5.0f,
            quotas = 12,
            status = LoanUiStatus.PAYED
        ),
        Loan(
            id = "2",
            presentedByName = "Laura Martínez",
            reason = "Compra Auto",
            total = 5_000.0f,
            payed = 1_500.0f,
            debt = 3_500.0f,
            interestRate = 7.5f,
            quotas = 24,
            status = LoanUiStatus.ACTIVE
        )
    )

    // userId not used, will be when using backend
    fun getPrestamosByUser(userId: Int): List<Loan> {
        return mockLoans
    }
}