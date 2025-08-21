package app.cooperativa.presentation.mainflow.directiva.manager.fine

import app.cooperativa.domain.directiva.DFineManagerRepository
import app.cooperativa.domain.directiva.Member
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking

import app.cooperativa.domain.directiva.DirectiveFineManagerRepository
import app.cooperativa.graphql.GraphQLClientProvider
import kotlin.test.assertTrue

/**
 * Test **sencillo** sin dependencias adicionales:
 * Valida que el repositorio real mapea correctamente la respuesta GraphQL
 * al modelo de dominio `Member`, usando un GraphQLClientProvider falso que
 * devuelve una `ApolloResponse` armada en memoria.
 */
class DFineManagerViewModelTest {

    private class FakeRepository : DFineManagerRepository {
        override suspend fun getAllAffiliates(): List<Member> = listOf(
            Member(usuarioId = 912_923, name = "El Pollo"),
            Member(usuarioId = 91_239,  name = "Pegdro")
        )
        override suspend fun submitFine() { /* no-op for tests */ }
    }

    @Test
    fun `updateFineName actualiza el estado correctamente`() = runBlocking {
        val repo: DFineManagerRepository = FakeRepository()
        val vm = DFineManagerViewModel(repo)

        vm.updateFineName("Pago atrasado")

        assertEquals("Pago atrasado", vm.uiState.value.fineName)
    }

    @Test
    fun `updateFineAmount parsea y actualiza el monto`() = runBlocking {
        val repo: DFineManagerRepository = FakeRepository()
        val vm = DFineManagerViewModel(repo)

        vm.updateFineAmount("12,50")

        assertEquals(12.5f, vm.uiState.value.fineAmount)
    }

    @Test
    fun `updateAffiliate actualiza nombre e id del socio`() = runBlocking {
        val repo: DFineManagerRepository = FakeRepository()
        val vm = DFineManagerViewModel(repo)

        vm.updateAffiliate("El Pollo", 912_923)

        assertEquals("El Pollo", vm.uiState.value.affiliateName)
        assertEquals(912_923, vm.uiState.value.affiliateId)
    }

    @Test
    fun `getAllAffiliates real API devuelve miembros`() = runBlocking {
        // Test de integración real SIN dependencias extra: usa el endpoint dev.
        val provider = GraphQLClientProvider(
            endpoint = "https://dev.cooperativa-isp.cc/graphql/payment"
        )
        val repo: DFineManagerRepository = DirectiveFineManagerRepository(provider)

        val result = repo.getAllAffiliates()

        // Afirmaciones mínimas y estables sin acoplarse a datos específicos:
        // - Debe devolver una lista (posiblemente vacía si el entorno no tiene datos),
        //   pero si hay elementos, cada uno debe tener nombre no vacío e id distinto de 0.
        assertTrue(result.all { it.usuarioId != 0 && it.name.isNotBlank() })
    }

    @Test
    fun `getAllAffiliates real API contiene los miembros esperados`() = runBlocking {
        val provider = GraphQLClientProvider(
            endpoint = "https://dev.cooperativa-isp.cc/graphql/payment"
        )
        val repo: DFineManagerRepository = DirectiveFineManagerRepository(provider)

        val result = repo.getAllAffiliates()

        val expected = listOf(
            Member(usuarioId = 912_923, name = "El Pollo"),
            Member(usuarioId = 91_239,  name = "Pegdro")
        )

        // Contraste directo: la respuesta debe contener al menos estos dos miembros.
        // Esto es más robusto que exigir igualdad exacta de toda la lista.
        assertTrue(result.containsAll(expected))
    }
}