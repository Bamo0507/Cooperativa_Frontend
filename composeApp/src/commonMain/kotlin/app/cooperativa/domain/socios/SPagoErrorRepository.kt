package app.cooperativa.domain.socios

interface SPagoErrorRepository {
    suspend fun getDirectiveErrorMessage(paymentId: String): String
}

// Mock Implementation of the repository
class MockSociosPagoErrorRepository : SPagoErrorRepository {
    override suspend fun getDirectiveErrorMessage(paymentId: String) = "Error al intentar registrar el pago. Verifique que ha llenado todos los campos correctamente, incluyendo el monto del pago y el n mero de la cuota. Si el error persiste, por favor comun quese con la Cooperativa."
}