//package app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus
//
//import app.cash.turbine.test
//import app.cooperativa.data.localdb.socios.SPagoStatusMockData
//import app.cooperativa.data.model.dto.PagosStatus
//import app.cooperativa.domain.socios.SPagosStatusRepository
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.StandardTestDispatcher
//import kotlinx.coroutines.test.advanceTimeBy
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import kotlin.test.AfterTest
//import kotlin.test.BeforeTest
//import kotlin.test.Test
//import kotlin.test.assertEquals
//import kotlin.test.assertFalse
//import kotlin.test.assertNull
//import kotlin.test.assertTrue
//
///**
// * Tests para [SPagosStatusViewModel].
// *
// * Se valida la secuencia de estados emitidos por el StateFlow cuando:
// *  1. La carga inicial (init -> loadData()) se ejecuta correctamente.
// *  2. Se produce un error en el repositorio.
// *  3. Se vuelve a invocar manualmente loadData() y se re-emite loading + datos.
// *
// * Los tests usan un [StandardTestDispatcher] conectado al [runTest] scheduler para
// * poder avanzar el tiempo virtual y así saltar el `delay(1500)` del ViewModel sin
// * esperar realmente 1.5s.
// */
//@OptIn(ExperimentalCoroutinesApi::class)
//class SPagosStatusViewModelTest {
//
//    // -------------------------------------------------------------------------
//    // Mocks
//    // -------------------------------------------------------------------------
//
//    /** Implementación mock "feliz" que devuelve la data de SPagoStatusMockData. */
//    private class MockSociosPagosStatusRepository : SPagosStatusRepository {
//        override suspend fun getPagoStatusByUser(userId: Int): List<PagosStatus> {
//            return SPagoStatusMockData.getPagosStatusByUser(userId)
//        }
//    }
//
//    /** Implementación mock que lanza excepción para probar el camino de error. */
//    private class ThrowingSociosPagosStatusRepository(
//        private val message: String = "Network error"
//    ) : SPagosStatusRepository {
//        override suspend fun getPagoStatusByUser(userId: Int): List<PagosStatus> {
//            throw RuntimeException(message)
//        }
//    }
//
//    // -------------------------------------------------------------------------
//    // Dispatcher management
//    // -------------------------------------------------------------------------
//
//    /**
//     * Guardamos el dispatcher de prueba para poder usarlo en funciones de avance
//     * de tiempo como [advanceTimeBy].
//     *
//     * Nota: El [StandardTestDispatcher] se crea en cada test dentro de `runTest`,
//     * de modo que usemos el `testScheduler` provisto por runTest. Por eso aquí no
//     * inicializamos nada; los @BeforeTest/@AfterTest son opcionales si prefieres
//     * hacerlo por test. Los dejo vacíos para claridad y para evitar errores de
//     * re-asignación multiplataforma.
//     */
//    @BeforeTest
//    fun before() {
//        // No-op: configuramos el Main dispatcher dentro de cada test, porque
//        // runTest crea su propio scheduler por invocación.
//    }
//
//    @AfterTest
//    fun after() {
//        // No-op: cada test resetea Main en su propio bloque try/finally.
//    }
//
//    // -------------------------------------------------------------------------
//    // TESTS
//    // -------------------------------------------------------------------------
//
//    @Test
//    fun `loadData emits initial - loading - loaded with mock data`() = runTest {
//        // Creamos un dispatcher vinculado al scheduler de este runTest
//        val mainDispatcher = StandardTestDispatcher(testScheduler)
//        Dispatchers.setMain(mainDispatcher)
//        try {
//            val repo = MockSociosPagosStatusRepository()
//            val vm = SPagosStatusViewModel(repo, userId = 1)
//
//            vm.uiState.test {
//                // 0) Estado inicial por defecto (del MutableStateFlow inicial)
//                val initial = awaitItem()
//                assertFalse(initial.isLoading)
//                assertTrue(initial.pagosStatus.isEmpty())
//                assertNull(initial.errorMessage)
//
//                // Avanzamos el dispatcher para que corra la corutina de loadData() hasta el delay
//                advanceUntilIdle() // ejecuta el launch hasta el primer suspension point (delay)
//
//                // 1) Se debió emitir estado de loading = true
//                val loading = awaitItem()
//                assertTrue(loading.isLoading)
//                assertNull(loading.errorMessage)
//
//                // Aún no hemos avanzado el tiempo del delay(1500) -> no debería haberse cargado datos
//                // Avanzamos el tiempo virtual 1500ms para completar el delay
//                advanceTimeBy(1500)
//                advanceUntilIdle() // procesa las tareas post-delay
//
//                // 2) Estado cargado con datos mock
//                val loaded = awaitItem()
//                assertFalse(loaded.isLoading)
//                assertEquals(SPagoStatusMockData.getPagosStatusByUser(1), loaded.pagosStatus)
//                assertNull(loaded.errorMessage)
//
//                cancelAndIgnoreRemainingEvents()
//            }
//        } finally {
//            Dispatchers.resetMain()
//        }
//    }
//
//    @Test
//    fun `loadData emits error state when repository throws`() = runTest {
//        val mainDispatcher = StandardTestDispatcher(testScheduler)
//        Dispatchers.setMain(mainDispatcher)
//        try {
//            val errorMessage = "Boom!"
//            val repo = ThrowingSociosPagosStatusRepository(errorMessage)
//            val vm = SPagosStatusViewModel(repo, userId = 1)
//
//            vm.uiState.test {
//                // 0) Inicial
//                val initial = awaitItem()
//                assertFalse(initial.isLoading)
//                assertTrue(initial.pagosStatus.isEmpty())
//                assertNull(initial.errorMessage)
//
//                // Dejar correr hasta primer delay
//                advanceUntilIdle()
//
//                // 1) Loading
//                val loading = awaitItem()
//                assertTrue(loading.isLoading)
//                assertNull(loading.errorMessage)
//
//                // Completar delay y ejecutar el bloque que lanza la excepción
//                advanceTimeBy(1500)
//                advanceUntilIdle()
//
//                // 2) Error
//                val errored = awaitItem()
//                assertFalse(errored.isLoading)
//                assertTrue(errored.pagosStatus.isEmpty()) // no hubo datos
//                // Debido a diferencias de plataforma, algunos runtimes incluyen clase en message.
//                // Verificamos que contenga el mensaje esperado.
//                assertTrue(errored.errorMessage?.contains(errorMessage) == true)
//
//                cancelAndIgnoreRemainingEvents()
//            }
//        } finally {
//            Dispatchers.resetMain()
//        }
//    }
//
//    @Test
//    fun `calling loadData again re-emits loading then refreshed data`() = runTest {
//        val mainDispatcher = StandardTestDispatcher(testScheduler)
//        Dispatchers.setMain(mainDispatcher)
//        try {
//            val repo = MockSociosPagosStatusRepository()
//            val vm = SPagosStatusViewModel(repo, userId = 1)
//
//            vm.uiState.test {
//                // 0) Inicial
//                val initial = awaitItem()
//                assertFalse(initial.isLoading)
//
//                // Ejecutar carga inicial
//                advanceUntilIdle()
//                val loading1 = awaitItem()
//                assertTrue(loading1.isLoading)
//
//                advanceTimeBy(1500)
//                advanceUntilIdle()
//                val loaded1 = awaitItem()
//                assertFalse(loaded1.isLoading)
//                assertEquals(SPagoStatusMockData.getPagosStatusByUser(1), loaded1.pagosStatus)
//
//                // --- Invocamos manualmente loadData() de nuevo ---
//                vm.loadData()
//
//                // Correr hasta el delay
//                advanceUntilIdle()
//                val loading2 = awaitItem()
//                assertTrue(loading2.isLoading)
//
//                advanceTimeBy(1500)
//                advanceUntilIdle()
//                val loaded2 = awaitItem()
//                assertFalse(loaded2.isLoading)
//                assertEquals(SPagoStatusMockData.getPagosStatusByUser(1), loaded2.pagosStatus)
//
//                cancelAndIgnoreRemainingEvents()
//            }
//        } finally {
//            Dispatchers.resetMain()
//        }
//    }
//}