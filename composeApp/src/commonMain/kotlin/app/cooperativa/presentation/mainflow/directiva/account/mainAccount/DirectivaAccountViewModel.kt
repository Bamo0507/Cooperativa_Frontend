package app.cooperativa.presentation.mainflow.directiva.account.mainAccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import kotlinx.coroutines.launch

class DirectivaAccountViewModel(
    private val prefs: PreferencesLocalStorage
): ViewModel() {
    fun logout() {
        viewModelScope.launch {
            prefs.clear()
        }
    }
}