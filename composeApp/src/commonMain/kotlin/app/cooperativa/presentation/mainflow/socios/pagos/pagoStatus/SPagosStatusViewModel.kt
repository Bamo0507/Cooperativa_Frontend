package app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.socios.SPagosStatusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SPagosStatusViewModel(
    private val repository: SPagosStatusRepository,
    private val userId: Int = 1
): ViewModel() {
    private val _uiState: MutableStateFlow<SPagosStatusState> = MutableStateFlow(
        SPagosStatusState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            // delay time 1.5s
            delay(1500)

            try {
                val pagosUser = repository.getPagoStatusByUser(userId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        pagosStatus = pagosUser
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

}