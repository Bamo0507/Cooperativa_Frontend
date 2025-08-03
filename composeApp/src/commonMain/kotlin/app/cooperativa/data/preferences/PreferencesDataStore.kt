package app.cooperativa.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import kotlinx.coroutines.flow.first

class PreferencesDataStore(
    private val dataStore: DataStore<Preferences>
):PreferencesLocalStorage {
    private val accessTokenKey = stringPreferencesKey("accessToken")
    private val hasLoggedInKey = stringPreferencesKey("hasLoggedIn")
    private val user_nameKey = stringPreferencesKey("user_name")
    private val pass_codeKey = stringPreferencesKey("pass_code")
    private val user_typeKey = stringPreferencesKey("user_type")

    override suspend fun setAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[accessTokenKey] = accessToken
        }
    }

    override suspend fun setHasLoggedIn(hasLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[hasLoggedInKey] = hasLoggedIn.toString()
        }
    }

    override suspend fun setUser_name(user_name: String) {
        dataStore.edit { preferences ->
            preferences[user_nameKey] = user_name
        }
    }

    override suspend fun setPass_code(pass_code: String) {
        dataStore.edit { preferences ->
            preferences[pass_codeKey] = pass_code
        }
    }

    override suspend fun setUser_type(user_type: String) {
        dataStore.edit { preferences ->
            preferences[user_typeKey] = user_type
        }
    }

    override suspend fun getAccessToken(): String {
        val preferences = dataStore.data.first()
        return preferences[accessTokenKey] ?: ""
    }

    override suspend fun getHasLoggedIn(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[hasLoggedInKey]?.toBoolean() ?: false
    }

    override suspend fun getUser_name(): String {
        val preferences = dataStore.data.first()
        return preferences[user_nameKey] ?: ""
    }

    override suspend fun getPass_code(): String {
        val preferences = dataStore.data.first()
        return preferences[pass_codeKey] ?: ""
    }

    override suspend fun getUser_type(): String {
        val preferences = dataStore.data.first()
        return preferences[user_typeKey] ?: "affiliate"
    }
}