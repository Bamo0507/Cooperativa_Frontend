package app.cooperativa.presentation.mainflow.socios.historial.mainHistorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.socios.SHistorialRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SHistorialViewModel(
    private val repository: SHistorialRepository,
    private val accessToken: String = "77656D82A042ABA5AE02293A880479D3DACA6609331486E01F351285990F6235"
): ViewModel() {
    private val _uiState: MutableStateFlow<SHistorialState> = MutableStateFlow(
        SHistorialState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val token = accessToken

                val history = repository.fetchHistory(token)
                val prestamosUser = repository.getPrestamosByUser(token)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        totalAportado = history.payedToCapital,
                        capitalPorPagar = history.owedCapital,
                        prestamos = prestamosUser
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

    fun switchTab(index: Int){
        _uiState.update { state ->
            state.copy(
                selectedTabIndex = index
            )
        }
    }
}