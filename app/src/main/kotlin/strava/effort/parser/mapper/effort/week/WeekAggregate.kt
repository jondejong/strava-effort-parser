package strava.effort.parser.mapper.week

import java.time.LocalDate

data class WeekAggregate(
    val start: LocalDate,
    val end: LocalDate,
    var effort: Int = 0
)
