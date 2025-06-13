package app.cooperativa.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun todayLocalDate(): LocalDate {
    // 1. Grab the current instant (a UTC timestamp)
    val nowInstant = Clock.System.now()
    // 2. Convert it to a LocalDateTime in the device’s time zone
    val localDateTime = nowInstant.toLocalDateTime(TimeZone.currentSystemDefault())
    // 3. Pull out just the date portion
    return localDateTime.date
}