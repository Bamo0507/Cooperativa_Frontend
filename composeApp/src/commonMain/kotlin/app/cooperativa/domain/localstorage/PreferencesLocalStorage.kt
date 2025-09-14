package app.cooperativa.domain.localstorage

interface PreferencesLocalStorage {
    suspend fun setAccessToken(accessToken: String)
    suspend fun setHasLoggedIn(hasLoggedIn: Boolean)
    suspend fun setUser_name(user_name: String)
    suspend fun setPass_code(pass_code: String)
    suspend fun setUser_type(user_type: String)

    suspend fun getAccessToken(): String
    suspend fun getHasLoggedIn(): Boolean
    suspend fun getUser_name(): String
    suspend fun getPass_code(): String
    suspend fun getUser_type(): String

    suspend fun hasSentPayment(): Boolean

    suspend fun clear()
}