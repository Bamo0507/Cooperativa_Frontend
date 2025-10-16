package app.cooperativa.data.model.dto

import app.cooperativa.graphql.type.FineStatus

data class FineItem(
    val id: String,
    val reason: String,
    val amount: Float,
    val status: FineStatus
)
