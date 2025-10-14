package app.cooperativa.domain.socios

import app.cooperativa.core.network.apollo.executeQuery
import app.cooperativa.data.model.dto.Estados
import app.cooperativa.data.model.dto.PagosStatus
import app.cooperativa.graphql.GetUsersPaymentsQuery
import app.cooperativa.graphql.type.PaymentStatus
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
                    pagoId = node.id,
                    nombrePago = node.name,
                    estado = node.state.toEstado(),
                    dateOfPayment = node.paymentDate,
                    commentary = node.commentary
                )
            }
        }
    }
}

fun PaymentStatus.toEstado(): Estados {
    return when (this) {
        PaymentStatus.ON_REVISION -> Estados.ON_REVISION
        PaymentStatus.REJECTED -> Estados.REJECTED
        PaymentStatus.ACCEPTED -> Estados.ACCEPTED
        else -> Estados.ON_REVISION
    }
}