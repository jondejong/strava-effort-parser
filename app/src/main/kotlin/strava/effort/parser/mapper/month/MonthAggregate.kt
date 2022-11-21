package strava.effort.parser.mapper.month

import java.time.LocalDate
import java.time.Month
import java.time.Year

data class MonthAggregate(
    val month: Month,
    val year: Int,
    var effort: Int = 0
)
