package app.cooperativa.presentation.mainflow.socios.pagos.agregarPago

import app.cooperativa.data.localdb.socios.SPagoEnviarMockData
import app.cooperativa.data.model.dto.FinePayAffiliate
import app.cooperativa.data.model.dto.LoanQuota
import app.cooperativa.data.model.dto.QuotaAffiliate
import app.cooperativa.data.preferences.FakeDataStore
import app.cooperativa.domain.socios.SPagoEnviarRepository
import app.cooperativa.graphql.type.PayedToInput
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
        override suspend fun getMonthlyAffiliateQuota(accessToken: String): List<QuotaAffiliate> =
            SPagoEnviarMockData.getCuotasMensualesPendientes()

        override suspend fun getPendingLoansQuotas(accessToken: String): List<LoanQuota> =
            SPagoEnviarMockData.getPrestamoCuotasByUser(1)

        override suspend fun getFinesByAccessToken(accessToken: String): List<FinePayAffiliate> =
            SPagoEnviarMockData.getPagoMultasByQuotasUser(listOf(1))

        override suspend fun createUserPayment(
            accessToken: String,
            name: String,
            totalAmount: Float,
            ticketNumber: String,
            accountNumber: String,
            beingPayed: List<PayedToInput>,
        ): String = "OK-MOCK"
    }

    /** Inyectamos un dispatcher de pruebas como Main para que viewModelScope use nuestro scheduler. */
    private fun setMainDispatcherForTest(testScheduler: kotlinx.coroutines.test.TestCoroutineScheduler) {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
    }

    // ------------------------------------------------------------------
    // Tests de update* simples
    // ------------------------------------------------------------------
    @Test
    fun `updateNombrePago truncates input to 30 chars`() = runTest {
        setMainDispatcherForTest(testScheduler)
        try {
            val vm = SPagoEnviarViewModel(MockSociosPagoEnviarRepository(), FakeDataStore())
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
            val vm = SPagoEnviarViewModel(MockSociosPagoEnviarRepository(), FakeDataStore())
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
            val vm = SPagoEnviarViewModel(MockSociosPagoEnviarRepository(), FakeDataStore())
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
     * Si la suma seleccionada != montoPago declarado, validateDeclaredAmount() debe
     * devolver false y errorMontoPago = true.
     */
    @Test
    fun `validateDeclaredAmount returns false when totals do not match montoPago`() = runTest {
        setMainDispatcherForTest(testScheduler)
        try {
            val vm = SPagoEnviarViewModel(MockSociosPagoEnviarRepository(), FakeDataStore())
            advanceUntilIdle()

            // Agregamos una sola cuota
            val cuota = vm.uiState.value.cuotasDisponibles.first()
            vm.addCuota(cuota)

            // Declaramos un monto diferente
            vm.updateMontoPago((cuota.montoCuota + 999f).toString())

            val isValid = vm.validateDeclaredAmount()
            val state = vm.uiState.value

            assertFalse(isValid)
            assertTrue(state.errorMontoPago)
        } finally {
            Dispatchers.resetMain()
        }
    }
    // ------------------------------------------------------------------
    // Tests de flujo completo de validación de monto declarado
    // ------------------------------------------------------------------
    @Test
    fun `full flow validation fails when declared amount mismatches`() = runTest {
        setMainDispatcherForTest(testScheduler)
        try {
            val vm = SPagoEnviarViewModel(MockSociosPagoEnviarRepository(), FakeDataStore())
            advanceUntilIdle()

            // Agregamos una sola cuota pero declaramos un monto incorrecto
            val cuota = vm.uiState.value.cuotasDisponibles.first()
            vm.addCuota(cuota)
            vm.updateMontoPago((cuota.montoCuota + 500f).toString()) // mismatch

            // Ejecutamos validación completa
            val isValid = vm.validateDeclaredAmount()
            val state = vm.uiState.value

            assertFalse(isValid)
            assertTrue(state.errorMontoPago)
        } finally {
            Dispatchers.resetMain()
        }
    }
}