package app.cooperativa.data.localdb

import kotlinx.datetime.LocalDate
import app.cooperativa.data.model.dto.Payment
import app.cooperativa.data.model.dto.Quotas
import app.cooperativa.data.model.dto.QuotaType
import app.cooperativa.data.model.dto.LoanPayment
import app.cooperativa.data.model.dto.FinePayment
import app.cooperativa.data.model.dto.Contribution
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
                    quotaType = QuotaType.ORDINARY,
                    date = LocalDate(2025, 5, 1)
                )
            ),
            loanPayments = listOf(
                LoanPayment(
                    loanName = "Préstamo Casa",
                    userName = "Juan Alberto Martínez Orellana",
                    amountPayed = 250.0f,
                    numberOfPayment = 1,
                    totalLoanPayments = 12,
                    date = LocalDate(2025, 5, 1)
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
                    quotaType = QuotaType.ORDINARY,
                    date = LocalDate(2025, 4, 28)
                ),
                Quotas(
                    userName = "María Fernanda López",
                    amount = 150.0f,
                    quotaType = QuotaType.ORDINARY,
                    date = LocalDate(2025, 4, 28)
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
                    totalLoanPayments = 24,
                    date = LocalDate(2025, 5, 3)
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
                    quotaType = QuotaType.EXTRAORDINARY,
                    date = LocalDate(2025, 4, 30)
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
                    quotaType = QuotaType.ORDINARY,
                    date = LocalDate(2025, 5, 5)
                )
            ),
            loanPayments = listOf(
                LoanPayment(
                    loanName = "Préstamo Estudiantil",
                    userName = "Luis Fernando Castillo",
                    amountPayed = 100.0f,
                    numberOfPayment = 2,
                    totalLoanPayments = 10,
                    date = LocalDate(2025, 5, 5)
                )
            ),
            paymentImage = "https://example.com/images/student_loan.png",
            isPaymentPending = false
        ),

        Payment(
            id = 6,
            paymentName = "Pago Multa Tardanza",
            userName = "Andrea Paola Jiménez",
            paymentDate = LocalDate(2025, 5, 6),
            quotas = null,
            loanPayments = null,
            finePayments = listOf(
                FinePayment(
                    user = "Andrea Paola Jiménez",
                    fineName = "Tardanza en reunión",
                    amount = 20.0f
                )
            ),
            paymentImage = "https://example.com/images/late_fee.png",
            isPaymentPending = true
        ),

        Payment(
            id = 7,
            paymentName = "Pago Aporte Mensual",
            userName = "Roberto Carlos Mejía",
            paymentDate = LocalDate(2025, 5, 7),
            quotas = null,
            loanPayments = null,
            contributionPayments = listOf(
                Contribution(
                    user = "Roberto Carlos Mejía",
                    amount = 50.0f
                )
            ),
            paymentImage = "https://example.com/images/contribution.png",
            isPaymentPending = false
        ),

        Payment(
            id = 8,
            paymentName = "Pago Completo Prueba",
            userName = "Sofía Gabriela Hernández",
            paymentDate = LocalDate(2025, 5, 8),
            quotas = listOf(
                Quotas(
                    userName = "Sofía Gabriela Hernández",
                    amount = 200.0f,
                    quotaType = QuotaType.ORDINARY,
                    date = LocalDate(2025, 5, 8)
                ),
                Quotas(
                    userName = "Sofía Gabriela Hernández",
                    amount = 50.0f,
                    quotaType = QuotaType.EXTRAORDINARY,
                    date = LocalDate(2025, 5, 8)
                )
            ),
            loanPayments = listOf(
                LoanPayment(
                    loanName = "Préstamo Personal",
                    userName = "Sofía Gabriela Hernández",
                    amountPayed = 300.0f,
                    numberOfPayment = 5,
                    totalLoanPayments = 12,
                    date = LocalDate(2025, 5, 8)
                )
            ),
            finePayments = listOf(
                FinePayment(
                    user = "Sofía Gabriela Hernández",
                    fineName = "Incumplimiento de normas",
                    amount = 35.0f
                )
            ),
            contributionPayments = listOf(
                Contribution(
                    user = "Sofía Gabriela Hernández",
                    amount = 75.0f
                )
            ),
            paymentImage = "https://example.com/images/full_payment.png",
            isPaymentPending = true
        ),
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
