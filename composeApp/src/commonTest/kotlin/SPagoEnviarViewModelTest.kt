package app.cooperativa.presentation.mainflow.socios.pagos.agregarPago

import app.cooperativa.data.localdb.socios.SPagoEnviarMockData
import app.cooperativa.data.model.dto.BasicUserInfo
import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.QuotaAffiliate
import app.cooperativa.domain.socios.SPagoEnviarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class SPagoEnviarViewModelTest {

    // ------------------------------------------------------------------
    // Mock repository que expone los datos fijos de SPagoEnviarMockData
    // ------------------------------------------------------------------
    private class MockSociosPagoEnviarRepository : SPagoEnviarRepository {
        override suspend fun getCuotasMensualesPendientes(): List<QuotaAffiliate> =
            SPagoEnviarMockData.getCuotasMensualesPendientes()

        override suspend fun getPrestamoCuotasByUser(userId: Int): List<LoanQuota> =
            SPagoEnviarMockData.getPrestamoCuotasByUser(userId)

        override suspend fun getPagoMultasByQuotasUser(userIds: List<Int>): List<FinePayAffiliate> =
            SPagoEnviarMockData.getPagoMultasByQuotasUser(userIds)

        override suspend fun getAllUsers(): List<BasicUserInfo> =
            SPagoEnviarMockData.getAllUsers()
    }

    /** Inyectamos un dispatcher de pruebas como Main para que viewModelScope use nuestro scheduler. */
    private fun setMainDispatcherForTest(testScheduler: kotlinx.coroutines.test.TestCoroutineScheduler) {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
    }

    // ------------------------------------------------------------------
    // Tests de carga inicial
    // ------------------------------------------------------------------
    @Test
    fun `loadData loads mock data into state`() = runTest {
        setMainDispatcherForTest(testScheduler)
        try {
            val repo = MockSociosPagoEnviarRepository()
            val vm = SPagoEnviarViewModel(repo, userId = 1)

            // Completar corrutinas de loadData lanzadas en init
            advanceUntilIdle()

            val state = vm.uiState.value
            assertFalse(state.isLoading)
            assertNull(state.errorMessage)
            assertEquals(SPagoEnviarMockData.getCuotasMensualesPendientes(), state.cuotasDisponibles)
            assertEquals(SPagoEnviarMockData.getPrestamoCuotasByUser(1), state.prestamosDisponibles)
            assertEquals(
                SPagoEnviarMockData.getPagoMultasByQuotasUser(listOf(1)),
                state.multasDisponibles
            )
            assertEquals(SPagoEnviarMockData.getAllUsers(), state.usuariosDisponibles)
        } finally {
            Dispatchers.resetMain()
        }
    }

    // ------------------------------------------------------------------
    // Tests de update* simples
    // ------------------------------------------------------------------
    @Test
    fun `updateNombrePago truncates input to 30 chars`() = runTest {
        setMainDispatcherForTest(testScheduler)
        try {
            val vm = SPagoEnviarViewModel(MockSociosPagoEnviarRepository())
            advanceUntilIdle()

            val longName = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" // > 30
            vm.updateNombrePago(longName)

            val state = vm.uiState.value
            assertEquals(30, state.nombrePago.length)
            assertEquals(longName.take(30), state.nombrePago)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `updateNumeroCuenta filters non-digit characters`() = runTest {
        setMainDispatcherForTest(testScheduler)
        try {
            val vm = SPagoEnviarViewModel(MockSociosPagoEnviarRepository())
            advanceUntilIdle()

            vm.updateNumeroCuenta("a1b2-3_4x5")
            val state = vm.uiState.value
            assertEquals("12345", state.numberoCuenta)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `updateNumeroBoleta filters non-digit characters`() = runTest {
        setMainDispatcherForTest(testScheduler)
        try {
            val vm = SPagoEnviarViewModel(MockSociosPagoEnviarRepository())
            advanceUntilIdle()

            vm.updateNumeroBoleta("boleta#6789!")
            val state = vm.uiState.value
            assertEquals("6789", state.numeroBoleta)
        } finally {
            Dispatchers.resetMain()
        }
    }

    // ------------------------------------------------------------------
    // Tests de validateDeclaredAmount()
    // ------------------------------------------------------------------

    /**
     * Cuando la suma seleccionada == montoPago (sincronizado con montoActualDeclarado en VM),
     * validateDeclaredAmount() debe devolver true y errorMontoPago = false.
     *
     * IMPORTANTE: asegúrate de haber aplicado el patch en updateMontoPago() que
     * también copia monto en montoActualDeclarado. Si no, este test fallará.
     */
    @Test
    fun `validateDeclaredAmount returns true when totals match montoPago`() = runTest {
        setMainDispatcherForTest(testScheduler)
        try {
            val vm = SPagoEnviarViewModel(MockSociosPagoEnviarRepository(), userId = 1)
            advanceUntilIdle()

            // Tomamos algunos datos del mock
            val cuota = vm.uiState.value.cuotasDisponibles.first()      // e.g., 100f
            val prestamo = vm.uiState.value.prestamosDisponibles.first()// e.g., 1000f
            val multa = vm.uiState.value.multasDisponibles.first()      // e.g., 10f
            val user = vm.uiState.value.usuariosDisponibles.first()

            // Los agregamos al estado
            vm.addCuota(cuota)
            vm.addLoanQuota(prestamo)
            vm.addFine(multa)
            vm.addCapitalContribution(user = user, amount = 50f)

            // Calculamos total y lo declaramos
            val total = cuota.montoCuota + prestamo.monto + multa.fineAmount + 50f
            vm.updateMontoPago(total)

            // Ejecutamos validación
            val isValid = vm.validateDeclaredAmount()
            val state = vm.uiState.value

            assertTrue(isValid)
            assertFalse(state.errorMontoPago)
        } finally {
            Dispatchers.resetMain()
        }
    }

    /**
     * Si la suma seleccionada != montoPago declarado, validateDeclaredAmount() debe
     * devolver false y errorMontoPago = true.
     */
    @Test
    fun `validateDeclaredAmount returns false when totals do not match montoPago`() = runTest {
        setMainDispatcherForTest(testScheduler)
        try {
            val vm = SPagoEnviarViewModel(MockSociosPagoEnviarRepository(), userId = 1)
            advanceUntilIdle()

            // Agregamos una sola cuota
            val cuota = vm.uiState.value.cuotasDisponibles.first()
            vm.addCuota(cuota)

            // Declaramos un monto diferente
            vm.updateMontoPago(cuota.montoCuota + 999f)

            val isValid = vm.validateDeclaredAmount()
            val state = vm.uiState.value

            assertFalse(isValid)
            assertTrue(state.errorMontoPago)
        } finally {
            Dispatchers.resetMain()
        }
    }
}