package app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.DPrestamoRepository
import app.cooperativa.domain.MockPrestamosRepository
import app.cooperativa.utils.PrestamoUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//De momento se manejara el mock repository qeu "fetchea" la mock data
class DPrestamoViewModel(
    private val repository: MockPrestamosRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<DPrestamoState> = MutableStateFlow(
        DPrestamoState()
    )
    val uiState = _uiState.asStateFlow()

    // Cargar la data desde que se inicia el viewModel
    init {
        loadData()
    }

    // Cargar toda la data que se maneja en la pantalla
    fun loadData() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val solicitudes = repository.fetchSolicitudes()
                val prestamos = repository.fetchPrestamosAprobados()
                val vigentes = prestamos.filter {
                    PrestamoUtils.countPaidInstallments(it) < it.plazoMeses
                }
                val completados = prestamos.filter {
                    PrestamoUtils.countPaidInstallments(it) == it.plazoMeses
                }

                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        reqLoans = solicitudes,
                        allLoans = prestamos,
                        prestamosVigentes = vigentes,
                        prestamosCompletados = completados
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    // Manejo de cambio de filtro
    fun onTabSelected(index: Int) {
        _uiState.update { state ->
            state.copy(
                selectedTabIndex = index,
                searchQuery = ""
            )
        }
    }

//    Actualizar el texto de busqueda
    fun onSearchQueryChange(query: String){
        _uiState.update { state ->
            state.copy(
                searchQuery = query
            )
        }
    }

}