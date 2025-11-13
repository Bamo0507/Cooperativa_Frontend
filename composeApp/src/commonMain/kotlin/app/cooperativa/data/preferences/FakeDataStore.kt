package app.cooperativa.data.preferences

import app.cooperativa.domain.localstorage.PreferencesLocalStorage
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Fake en memoria para tests. No persiste nada en disco.
 * Puedes ajustar valores iniciales desde el constructor o
 * mediante los setters suspend.
 */
class FakeDataStore(
    accessToken: String = "TEST_TOKEN",
    hasLoggedIn: Boolean = true,
    userName: String = "test_user",
    passCode: String = "000000",
    userType: String = "directive",
    private var hasSentPaymentFlag: Boolean = false
) : PreferencesLocalStorage {

    private val mutex = Mutex()

    private var _accessToken = accessToken
    private var _hasLoggedIn = hasLoggedIn
    private var _userName = userName
    private var _passCode = passCode
    private var _userType = userType

    // --- Setters ---
    override suspend fun setAccessToken(accessToken: String) = mutex.withLock {
        _accessToken = accessToken
    }

    override suspend fun setHasLoggedIn(hasLoggedIn: Boolean) = mutex.withLock {
        _hasLoggedIn = hasLoggedIn
    }

    override suspend fun setUser_name(user_name: String) = mutex.withLock {
        _userName = user_name
    }

    override suspend fun setPass_code(pass_code: String) = mutex.withLock {
        _passCode = pass_code
    }

    override suspend fun setUser_type(user_type: String) = mutex.withLock {
        _userType = user_type
    }

    // --- Getters ---
    override suspend fun getAccessToken(): String = mutex.withLock { _accessToken }

    override suspend fun getHasLoggedIn(): Boolean = mutex.withLock { _hasLoggedIn }

    override suspend fun getUser_name(): String = mutex.withLock { _userName }

    override suspend fun getPass_code(): String = mutex.withLock { _passCode }

    override suspend fun getUser_type(): String = mutex.withLock { _userType }

    override suspend fun hasSentPayment(): Boolean = mutex.withLock { hasSentPaymentFlag }

    /**
     * Helper para tests: permite setear el flag de envío de pago
     * (no forma parte de la interfaz de producción).
     */
    suspend fun setHasSentPaymentForTest(value: Boolean) = mutex.withLock {
        hasSentPaymentFlag = value
    }

    // --- Limpieza ---
    override suspend fun clear() = mutex.withLock {
        _accessToken = ""
        _hasLoggedIn = false
        _userName = ""
        _passCode = ""
        _userType = ""
        hasSentPaymentFlag = false
    }
}
