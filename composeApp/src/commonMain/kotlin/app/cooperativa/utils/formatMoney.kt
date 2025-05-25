package app.cooperativa.utils

import kotlin.math.absoluteValue
import kotlin.math.roundToInt

fun formatMoney(amount: Float): String {
    val integerPart = amount.toInt()
    val fractionPart = ((amount - integerPart) * 100).roundToInt().absoluteValue
    val cents = fractionPart.toString().padStart(2, '0')
    return "Q$integerPart.$cents"
}