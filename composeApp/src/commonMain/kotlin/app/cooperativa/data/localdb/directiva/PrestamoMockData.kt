package app.cooperativa.data.localdb.directiva

import app.cooperativa.data.model.dto.Loan
import app.cooperativa.data.model.dto.LoanUiStatus

object PrestamoMockData {
    private val mockLoans = listOf(
        Loan(
            id = "1",
            presentedByName = "Laura Martínez",
            reason = "Préstamo Vivienda",
            total = 10000.0f,
            payed = 10000.0f,
            debt = 0.0f,
            interestRate = 5.0f,
            quotas = 12,
            status = LoanUiStatus.PAYED
        ),
        Loan(
            id = "2",
            presentedByName = "Miguel Rodríguez",
            reason = "Préstamo Vehículo",
            total = 5000.0f,
            payed = 1500.0f,
            debt = 3500.0f,
            interestRate = 7.5f,
            quotas = 24,
            status = LoanUiStatus.ACTIVE
        ),
        Loan(
            id = "3",
            presentedByName = "Pedro Rodríguez",
            reason = "Préstamo Estudiantil",
            total = 8000.0f,
            payed = 0.0f,
            debt = 8000.0f,
            interestRate = 4.0f,
            quotas = 10,
            status = LoanUiStatus.PENDING
        ),
        Loan(
            id = "4",
            presentedByName = "Luis Rodríguez",
            reason = "Préstamo Emergencia",
            total = 2000.0f,
            payed = 0.0f,
            debt = 2000.0f,
            interestRate = 6.0f,
            quotas = 6,
            status = LoanUiStatus.PENDING
        )
    )

    fun getAllLoans(): List<Loan> = mockLoans

    @Deprecated("Usa getAllLoans()", ReplaceWith("getAllLoans()"))
    fun getAllPrestamos(): List<Loan> = mockLoans
}