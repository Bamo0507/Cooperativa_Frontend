package app.cooperativa.presentation.mainflow.directiva.prestamos.prestamosGeneral

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.data.model.dto.Prestamo
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

//    Filtrar acorde a lo que se vaya colocando en la search bar
    private fun filterByQuery(
        loans: List<Prestamo>,
        query: String
    ): List<Prestamo> =
        if (query.isBlank()) loans
        else loans.filter {
            it.nombreSolicitante.contains(query, ignoreCase = true) || it.nombre.contains(query, ignoreCase = true)
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

        _uiState.value.let { state ->
            val vigentes = state.allLoans.filter { PrestamoUtils.countPaidInstallments(it) < it.plazoMeses }
            val completados  = state.allLoans.filter { PrestamoUtils.countPaidInstallments(it) == it.plazoMeses }
            _uiState.update {
                it.copy(
                    prestamosVigentes = vigentes,
                    prestamosCompletados = completados
                )
            }
        }
    }

//    Actualizar el texto de busqueda
    fun onSearchQueryChange(query: String){
        _uiState.update { state ->
            state.copy(
                searchQuery = query
            )
        }

        _uiState.value.let { state ->
            val vigentes = state.allLoans.filter { PrestamoUtils.countPaidInstallments(it) < it.plazoMeses }
            val complet  = state.allLoans.filter { PrestamoUtils.countPaidInstallments(it) == it.plazoMeses }
            _uiState.update {
                it.copy(
                    prestamosVigentes = filterByQuery(vigentes, query),
                    prestamosCompletados = filterByQuery(complet, query)
                )
            }
        }
    }

}