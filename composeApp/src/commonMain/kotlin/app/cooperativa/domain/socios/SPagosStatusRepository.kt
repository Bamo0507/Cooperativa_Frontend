package app.cooperativa.domain.socios

import app.cooperativa.core.network.apollo.executeQuery
import app.cooperativa.data.localdb.socios.SPagoStatusMockData
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.data.model.dto.PagosStatus
import app.cooperativa.graphql.GetUsersPaymentsQuery
import com.apollographql.apollo3.ApolloClient

interface SPagosStatusRepository {
    suspend fun getPagoStatusByUser(accessToken: String): List<PagosStatus>
}

class SociosPagosStatusRepository(
    private val apollo: ApolloClient
) : SPagosStatusRepository {

    override suspend fun getPagoStatusByUser(accessToken: String): List<PagosStatus> {
        return apollo.executeQuery(
            GetUsersPaymentsQuery(accessToken = accessToken)
        ) { data ->
            data.getUsersPayments.map { node ->
                PagosStatus(
                    pagoId = node.ticketNum,
                    nombrePago = node.commentary,
                    estado = node.state.toEstado(),
                    dateOfPayment = node.paymentDate
                )
            }
        }
    }
}

/** Backend envía "PENDING" | "COMPLETED" | "ON_REVISION" (luego cambiará a REJECTED) */
private fun String.toEstado(): Estados = when (uppercase()) {
    "PENDING"     -> Estados.PENDING
    "COMPLETED"   -> Estados.COMPLETED
    "ON_REVISION" -> Estados.ON_REVISION // cuando cambien a REJECTED, se ajusta
    "REJECTED"    -> Estados.ON_REVISION   // compat hacia futuro
    else -> Estados.PENDING
}
