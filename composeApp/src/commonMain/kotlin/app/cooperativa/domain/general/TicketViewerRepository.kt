package app.cooperativa.domain.general

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess

data class TicketResult(
    val url: String? = null,
    val bytes: ByteArray? = null
)

interface TicketViewerRepository {
    suspend fun fetchTicket(accessToken: String, ticketId: String): TicketResult
}

class TicketViewerRepositoryImpl(
    private val http: HttpClient
) : TicketViewerRepository {

    override suspend fun fetchTicket(accessToken: String, ticketId: String): TicketResult {
        val url = "https://dev.cooperativa-isp.cc/general/get_ticket_payment"
        val resp = http.get(url) {
            url {
                parameters.append("access_token", accessToken)
                parameters.append("ticket_id", ticketId)
            }
            headers.append(HttpHeaders.Accept, "*/*") // aceptamos imagen o texto
        }

        if (!resp.status.isSuccess()) {
            val bodyText = kotlin.runCatching { resp.body<String>() }.getOrNull()
            throw RuntimeException("Get ticket failed: ${resp.status.value} ${bodyText ?: ""}".trim())
        }

        val ct = resp.headers[HttpHeaders.ContentType]?.lowercase().orEmpty()
        if (ct.startsWith("image/")) {
            val bytes = resp.body<ByteArray>()
            return TicketResult(bytes = bytes)
        }

        val text = kotlin.runCatching { resp.body<String>() }.getOrNull()?.trim()
        if (!text.isNullOrBlank() && (text.startsWith("http://") || text.startsWith("https://"))) {
            return TicketResult(url = text)
        }

        if (!text.isNullOrBlank()) {
            val maybeUrl = Regex("""https?://[^\s"']+""").find(text)?.value
            if (!maybeUrl.isNullOrBlank()) return TicketResult(url = maybeUrl)
        }

        // Nada util
        throw RuntimeException("No se pudo obtener una imagen o URL válida de la boleta")
    }
}