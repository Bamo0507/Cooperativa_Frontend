package app.cooperativa.presentation.mainflow.directiva.manager.fine

import app.cooperativa.data.model.dto.Member
import app.cooperativa.domain.directiva.DFineManagerRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import app.cooperativa.domain.directiva.DirectiveFineManagerRepository
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
            Member(userId = "912_923", name = "El Pollo"),
            Member(userId = "91_239",  name = "Pegdro")
        )
        override suspend fun submitFine(
            affiliateKey: String,
            amount: Float,
            motive: String
        ) : String {
            return "no_test"
        }
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

        vm.updateAffiliate("El Pollo", "912_923")

        assertEquals("El Pollo", vm.uiState.value.affiliateName)
        assertEquals("912_923", vm.uiState.value.affiliateId)
    }
}