package app.cooperativa.presentation.mainflow.directiva.prestamos.loanRequestDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.directiva.DSolicitudPrestamoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SolicitudPrestamoViewModel(
    private val repository: DSolicitudPrestamoRepository,
    private val solicitudId: Int = 0
): ViewModel() {
    private val _uiState: MutableStateFlow<SolicitudPrestamoState> = MutableStateFlow(
        SolicitudPrestamoState(
            isLoading = true
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadSolicitud()
    }

    // Cargar la soliciutd
    fun loadSolicitud() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            delay(1500)

            try {
                val solicitud = repository.getSolicitudById(id = solicitudId)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        prestamo = solicitud
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

    // colocar valor de interes
    fun onInterestChange(interest: Float) {
        _uiState.update { state ->
            state.copy(
                interestInput = interest
            )
        }
    }

    // colocar comentarios
    fun onCommentsChange(comments: String) {
        _uiState.update { state ->
            state.copy(
                commentsInput = comments
            )
        }
    }

    // logica para aprobar solicitud
    fun onApprove(){
        // TODO: implementar accion
    }

    // logica para rechazar solicitud
    fun onReject(){
        // TODO: implementar accion
    }

}