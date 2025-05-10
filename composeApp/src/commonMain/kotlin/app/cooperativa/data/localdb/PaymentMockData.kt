package app.cooperativa.data.localdb

import kotlinx.datetime.LocalDate
import app.cooperativa.data.model.dto.Payment
import app.cooperativa.data.model.dto.Quotas
import app.cooperativa.data.model.dto.QuotaType
import app.cooperativa.data.model.dto.LoanPayment
import app.cooperativa.data.model.ui.BasicInfoPayment

object PaymentMockData {

    private val mockPayments = listOf(
        Payment(
            id = 1,
            paymentName = "Pago Préstamo Casa",
            userName = "Juan Alberto Martínez Orellana",
            paymentDate = LocalDate(2025, 5, 1),
            quotas = listOf(
                Quotas(
                    userName = "Juan Alberto Martínez Orellana",
                    amount = 250.0f,
                    quotaType = QuotaType.ORDINARY
                )
            ),
            loanPayments = listOf(
                LoanPayment(
                    loanName = "Préstamo Casa",
                    userName = "Juan Alberto Martínez Orellana",
                    amountPayed = 250.0f,
                    numberOfPayment = 1,
                    totalLoanPayments = 12
                )
            ),
            paymentImage = "https://example.com/images/house_loan.png",
            isPaymentPending = true
        ),

        Payment(
            id = 2,
            paymentName = "Pago Tarjeta Crédito",
            userName = "María Fernanda López",
            paymentDate = LocalDate(2025, 4, 28),
            quotas = listOf(
                Quotas(
                    userName = "María Fernanda López",
                    amount = 150.0f,
                    quotaType = QuotaType.ORDINARY
                ),
                Quotas(
                    userName = "María Fernanda López",
                    amount = 150.0f,
                    quotaType = QuotaType.ORDINARY
                )
            ),
            loanPayments = null,
            paymentImage = "https://example.com/images/credit_card.png",
            isPaymentPending = false
        ),

        Payment(
            id = 3,
            paymentName = "Pago Préstamo Vehículo",
            userName = "Carlos Eduardo Gómez",
            paymentDate = LocalDate(2025, 5, 3),
            quotas = null,
            loanPayments = listOf(
                LoanPayment(
                    loanName = "Préstamo Vehículo",
                    userName = "Carlos Eduardo Gómez",
                    amountPayed = 300.0f,
                    numberOfPayment = 3,
                    totalLoanPayments = 24
                )
            ),
            paymentImage = "https://example.com/images/car_loan.png",
            isPaymentPending = true
        ),

        Payment(
            id = 4,
            paymentName = "Pago Servicio Internet",
            userName = "Ana Patricia Morales",
            paymentDate = LocalDate(2025, 4, 30),
            quotas = listOf(
                Quotas(
                    userName = "Ana Patricia Morales",
                    amount = 40.0f,
                    quotaType = QuotaType.EXTRAORDINARY
                )
            ),
            loanPayments = null,
            paymentImage = "https://example.com/images/internet_bill.png",
            isPaymentPending = false
        ),

        Payment(
            id = 5,
            paymentName = "Pago Préstamo Estudiantil",
            userName = "Luis Fernando Castillo",
            paymentDate = LocalDate(2025, 5, 5),
            quotas = listOf(
                Quotas(
                    userName = "Luis Fernando Castillo",
                    amount = 100.0f,
                    quotaType = QuotaType.ORDINARY
                )
            ),
            loanPayments = listOf(
                LoanPayment(
                    loanName = "Préstamo Estudiantil",
                    userName = "Luis Fernando Castillo",
                    amountPayed = 100.0f,
                    numberOfPayment = 2,
                    totalLoanPayments = 10
                )
            ),
            paymentImage = "https://example.com/images/student_loan.png",
            isPaymentPending = false
        )
    )

    fun getAllPaymentsBasicInfo(): List<BasicInfoPayment> = mockPayments.map {
        BasicInfoPayment(
            id = it.id,
            paymentName = it.paymentName,
            username = it.userName,
            isPaymentPending = it.isPaymentPending
        )
    }

    /** Devuelve toda la lista de pagos de prueba */
    fun getAllPayments(): List<Payment> = mockPayments

    /**
     * Busca un pago por su ID.
     * @return el Payment correspondiente, o null si no existe.
     */
    fun getPaymentById(id: Int): Payment? =
        mockPayments.find { it.id == id }
}