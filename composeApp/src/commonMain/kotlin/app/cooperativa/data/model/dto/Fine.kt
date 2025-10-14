package app.cooperativa.data.model.dto

import kotlinx.datetime.LocalDate

data class Fine(
    val userId: Int,
    val userName: String,
    val fineDetails: List<FineDetail>
)

data class FineDetail(
    val id: Int,
    val name: String = "",
    val date: LocalDate,
    val amount: Float,
)
