package app.cooperativa.data.model.dto

enum class QuotaType {
    ORDINARY,
    EXTRAORDINARY
}

fun QuotaType.getQuotaAmount(): Float {
    return when(this) {
        QuotaType.ORDINARY -> 250f
        QuotaType.EXTRAORDINARY -> 1050f
    }
}