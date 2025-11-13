package app.cooperativa.data.localdb.directiva

import app.cooperativa.data.model.dto.Payment
import app.cooperativa.data.model.ui.BasicInfoPayment
import app.cooperativa.graphql.type.PaymentStatus

object PaymentMockData {

    // Mock data adaptada al nuevo modelo Payment
    private val mockPayments = listOf(
        Payment(
            id = "1",
            name = "Pago Préstamo Casa",
            presentedByName = "Juan Alberto Martínez Orellana",
            commentary = "Pago de la primera cuota del préstamo de la casa",
            paymentDate = "2025-05-01",
            state = PaymentStatus.ON_REVISION,           // antes isPaymentPending = true
            ticketNum = "TCK-0001",
            photoPath = "https://example.com/images/house_loan.png",
            totalAmount = 250.0f,
            accountNum = "ACC-0001"
        ),

        Payment(
            id = "2",
            name = "Pago Tarjeta Crédito",
            presentedByName = "María Fernanda López",
            commentary = "Pago de tarjeta de crédito del mes",
            paymentDate = "2025-04-28",
            state = PaymentStatus.ACCEPTED,
            ticketNum = "TCK-0002",
            photoPath = "https://example.com/images/credit_card.png",
            totalAmount = 300.0f,
            accountNum = "ACC-0002"
        ),

        Payment(
            id = "3",
            name = "Pago Préstamo Vehículo",
            presentedByName = "Carlos Eduardo Gómez",
            commentary = "Cuota 3 de préstamo de vehículo",
            paymentDate = "2025-05-03",
            state = PaymentStatus.ON_REVISION,          // antes isPaymentPending = true
            ticketNum = "TCK-0003",
            photoPath = "https://example.com/images/car_loan.png",
            totalAmount = 300.0f,
            accountNum = "ACC-0003"
        ),

        Payment(
            id = "4",
            name = "Pago Servicio Internet",
            presentedByName = "Ana Patricia Morales",
            commentary = "Pago mensual de servicio de internet",
            paymentDate = "2025-04-30",
            state = PaymentStatus.ACCEPTED,        // antes isPaymentPending = false
            ticketNum = "TCK-0004",
            photoPath = "https://example.com/images/internet_bill.png",
            totalAmount = 40.0f,
            accountNum = "ACC-0004"
        ),

        Payment(
            id = "5",
            name = "Pago Préstamo Estudiantil",
            presentedByName = "Luis Fernando Castillo",
            commentary = "Cuota de préstamo estudiantil",
            paymentDate = "2025-05-05",
            state = PaymentStatus.ACCEPTED,        // antes isPaymentPending = false
            ticketNum = "TCK-0005",
            photoPath = "https://example.com/images/student_loan.png",
            totalAmount = 100.0f,
            accountNum = "ACC-0005"
        ),

        Payment(
            id = "6",
            name = "Pago Multa Tardanza",
            presentedByName = "Andrea Paola Jiménez",
            commentary = "Multa por tardanza en reunión",
            paymentDate = "2025-05-06",
            state = PaymentStatus.ON_REVISION,          // antes isPaymentPending = true
            ticketNum = "TCK-0006",
            photoPath = "https://example.com/images/late_fee.png",
            totalAmount = 20.0f,
            accountNum = "ACC-0006"
        ),

        Payment(
            id = "7",
            name = "Pago Aporte Mensual",
            presentedByName = "Roberto Carlos Mejía",
            commentary = "Aporte mensual a la cooperativa",
            paymentDate = "2025-05-07",
            state = PaymentStatus.ACCEPTED,        // antes isPaymentPending = false
            ticketNum = "TCK-0007",
            photoPath = "https://example.com/images/contribution.png",
            totalAmount = 50.0f,
            accountNum = "ACC-0007"
        ),

        Payment(
            id = "8",
            name = "Pago Completo Prueba",
            presentedByName = "Sofía Gabriela Hernández",
            commentary = "Pago con cuotas, préstamo, multa y aporte (ejemplo completo)",
            paymentDate = "2025-05-08",
            state = PaymentStatus.ON_REVISION,          // antes isPaymentPending = true
            ticketNum = "TCK-0008",
            photoPath = "https://example.com/images/full_payment.png",
            totalAmount = 660.0f,
            accountNum = "ACC-0008"
        ),
    )

    fun getAllPaymentsBasicInfo(): List<BasicInfoPayment> = mockPayments.map {
        BasicInfoPayment(
            id = it.id,
            paymentName = it.name,
            username = it.presentedByName,
            // Derivamos el "pendiente" del estado
            isPaymentPending = it.state == PaymentStatus.ON_REVISION,
            dateOfPayment = it.paymentDate
        )
    }

    /** Devuelve toda la lista de pagos de prueba */
    fun getAllPayments(): List<Payment> = mockPayments

    /**
     * Busca un pago por su ID.
     * @return el Payment correspondiente, o null si no existe.
     */
    fun getPaymentById(id: String): Payment? =
        mockPayments.find { it.id == id }
}