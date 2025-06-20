package app.cooperativa.presentation.mainflow.socios.prestamos.mainPrestamos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.socios.SPrestamoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SPrestamoViewModel(
    private val repository: SPrestamoRepository,
    private val userId: Int = 1 // TODO: remove default value, make an on resume action that triggers splash, and refetches user id
): ViewModel() {
    private val _uiState: MutableStateFlow<SPrestamoState> = MutableStateFlow(
        SPrestamoState()
    )

    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData(){
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            delay(1500)

            try {
                val prestamosUser = repository.getPrestamosSolicitudesByUser(userId)
                val pagaresUser = repository.getPagaresSolicitudesByUser(userId)

                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        prestamos = prestamosUser,
                        pagares = pagaresUser
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

    fun onTabSelected(tabIndex: Int) {
        _uiState.update { state ->
            state.copy(
                selectedTabIndex = tabIndex
            )
        }
    }

}