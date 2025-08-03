package app.cooperativa.presentation.mainflow.socios.account.mainAccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import app.cooperativa.presentation.mainflow.socios.historial.mainHistorial.SHistorialState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SAccountViewModel(
    private val prefs: PreferencesLocalStorage
): ViewModel() {
    private val _uiState: MutableStateFlow<SAccountState> = MutableStateFlow(
        SAccountState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData(){
        viewModelScope.launch {
            _uiState.value = SAccountState(
                userType = prefs.getUser_type()
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            prefs.clear()
        }
    }
}