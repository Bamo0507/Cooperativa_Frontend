package app.cooperativa.data.model.dto

import kotlinx.datetime.LocalDate

data class Quotas(
    val userName: String,
    val amount: Float,
    val quotaType: QuotaType,
    val date: LocalDate
)