package strava.effort.parser.mapper.month

import java.time.Month

data class MonthAggregate(
    val month: Month,
    val year: Int,
    var effort: Int = 0
)
