package strava.effort.parser.mapper.activity

import java.time.LocalDate

data class ActivityDistanceWeekAggregate(
    val start: LocalDate,
    val end: LocalDate,
    var miles: Float = 0.0f
)