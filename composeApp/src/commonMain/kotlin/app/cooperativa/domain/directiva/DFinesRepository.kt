package app.cooperativa.domain.directiva

import app.cooperativa.data.localdb.directiva.FineMockData
import app.cooperativa.data.model.dto.Fine
import app.cooperativa.data.model.dto.FineItem
import app.cooperativa.graphql.EditFineMutation
import app.cooperativa.graphql.GetFinesByIdQuery
import app.cooperativa.graphql.type.FineStatus
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional

interface DFinesRepository {
    suspend fun getFinesByAccessKey(accessKey: String): List<FineItem>
    suspend fun editFine(fineKey: String, newAmount: Float, newMotive: String, newStatus: FineStatus): String
}

class FinesRepository(
    private val fineApollo: ApolloClient // named("fine")
) : DFinesRepository {

    override suspend fun getFinesByAccessKey(accessKey: String): List<FineItem> {
        val resp = fineApollo.query(GetFinesByIdQuery(accessKey)).execute()
        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (getFinesById)" })
        }
        val list = resp.data?.getFinesById ?: emptyList()
        return list.map {
            FineItem(
                id = it.id,
                reason = it.reason,
                amount = it.amount.toFloat(),
                status = it.status
            )
        }
    }

    override suspend fun editFine(
        fineKey: String,
        newAmount: Float,
        newMotive: String,
        newStatus: FineStatus
    ): String {
        val resp = fineApollo.mutation(
            EditFineMutation(
                fineKey = fineKey,
                newAmount = newAmount?.let { Optional.Present(it.toDouble()) } ?: Optional.Absent,
                newMotive = newMotive?.let { Optional.Present(it) } ?: Optional.Absent,
                newStatus = newStatus?.let { Optional.Present(it) } ?: Optional.Absent
            )
        ).execute()

        if (resp.hasErrors()) {
            val msg = resp.errors?.joinToString { it.message }.orEmpty()
            throw RuntimeException(msg.ifBlank { "Error GraphQL (editFine)" })
        }
        return resp.data?.editFine ?: "OK"
    }
}
