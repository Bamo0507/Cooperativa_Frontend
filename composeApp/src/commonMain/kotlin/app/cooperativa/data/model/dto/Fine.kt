package app.cooperativa.data.model.dto

import kotlinx.datetime.LocalDate

data class Fine(
    val userId: String,
    val userName: String,
    val fineDetails: List<FineDetail>
)

data class FineDetail(
    val id: String,
    val name: String = "",
    val date: LocalDate,
    val amount: Float,
)
