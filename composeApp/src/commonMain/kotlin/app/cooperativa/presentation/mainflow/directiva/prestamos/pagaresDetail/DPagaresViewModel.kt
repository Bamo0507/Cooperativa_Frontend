package app.cooperativa.presentation.mainflow.directiva.prestamos.pagaresDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.directiva.DPagaresRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DPagaresViewModel(
    private val repository: DPagaresRepository,
    private val pagareId: Int
): ViewModel() {
    private val _uiState: MutableStateFlow<DPagaresState> = MutableStateFlow(
        DPagaresState(
            isLoading = true
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadPagare()
    }

    fun loadPagare() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            delay(1500)

            try {
                val solicitud = repository.fetchPagareById(pagareId)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        pagare = solicitud
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