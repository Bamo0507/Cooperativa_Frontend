package app.cooperativa.presentation.mainflow.socios.historial.mainHistorial

import app.cooperativa.data.model.dto.Prestamo

data class SHistorialState(
    val selectedTabIndex: Int = 0,

    var totalAportado: Float = 0.0f,
    var capitalPorPagar: Float = 0.0f,
    var prestamos: List<Prestamo> = emptyList(),

    var isLoading: Boolean = false,
    var errorMessage: String? = null
)
