package app.cooperativa.core.network.ktor.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadTicketOk(
    @SerialName("ticket_id") val ticketId: String
)

@Serializable
data class UploadTicketError(
    @SerialName("message") val message: String
)

@Serializable
data class UploadTicketResponse(
    @SerialName("Ok") val ok: UploadTicketOk? = null,
    @SerialName("Err") val error: UploadTicketError? = null
)