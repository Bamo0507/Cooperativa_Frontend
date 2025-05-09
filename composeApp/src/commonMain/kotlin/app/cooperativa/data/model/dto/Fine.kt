package app.cooperativa.data.model.dto

import kotlinx.datetime.LocalDate

data class Fine(
    val userName: String,
    val fineDetails: List<FineDetail>
)

data class FineDetail(
    val name: String = "",
    val date: LocalDate,
    val amount: Float,
    val type: FineType
)
