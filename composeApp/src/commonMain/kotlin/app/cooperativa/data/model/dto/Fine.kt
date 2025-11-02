package app.cooperativa.data.model.dto

data class Fine(
    val userId: String,
    val userName: String,
    val fineDetails: List<FineDetail>
)

data class FineDetail(
    val id: String,
    val name: String = "",
    val amount: Float,
)
