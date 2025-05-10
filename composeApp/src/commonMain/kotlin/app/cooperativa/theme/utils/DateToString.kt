package app.cooperativa.theme.utils

import kotlinx.datetime.LocalDate

fun dateToString(date: LocalDate): String {
    val meses = listOf(
        "enero", "febrero", "marzo", "abril", "mayo", "junio",
        "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    )

    val nombreMes = meses[date.monthNumber - 1].replaceFirstChar { it.uppercaseChar() }
    return "$nombreMes ${date.year}"
}