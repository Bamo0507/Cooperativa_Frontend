package app.cooperativa.utils

import app.cooperativa.data.localdb.PrestamoMockData
import app.cooperativa.data.model.dto.Prestamo

object PrestamoUtils {
    /**
     * Cuenta cuántas cuotas están completamente pagadas.
     */
    fun countPaidInstallments(prestamo: Prestamo): Int =
        prestamo.mensualidadesPrestamo.count { it.montoPagado == it.montoCuota }

    /**
     * Calcula el monto total pagado según los detalles.
     */
    fun totalPaidAmount(prestamo: Prestamo): Float =
        prestamo.mensualidadesPrestamo.fold(0f) { acumulado, detalle ->
            acumulado + detalle.montoPagado
    }

    /**
     * Calcula el monto pendiente del préstamo.
     */
    fun remainingAmount(prestamo: Prestamo): Float =
        prestamo.montoTotal - totalPaidAmount(prestamo)

    /**
     * Devuelve todos los préstamos que ya fueron completamente pagados.
     */
    fun getCompletedPrestamos(): List<Prestamo> =
        PrestamoMockData.getAllPrestamos().filter {
            countPaidInstallments(it) == it.plazoMeses
        }

    /**
     * Devuelve todos los préstamos que aún no están completamente pagados.
     */
    fun getPendingPrestamos(): List<Prestamo> =
        PrestamoMockData.getAllPrestamos().filter {
            countPaidInstallments(it) < it.plazoMeses
        }
}