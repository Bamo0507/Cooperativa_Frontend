package app.cooperativa.presentation.mainflow.socios.account.mainAccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import kotlinx.coroutines.launch

class SAccountViewModel(
    private val prefs: PreferencesLocalStorage
): ViewModel() {
    fun logout() {
        viewModelScope.launch {
            prefs.clear()
        }
    }
}