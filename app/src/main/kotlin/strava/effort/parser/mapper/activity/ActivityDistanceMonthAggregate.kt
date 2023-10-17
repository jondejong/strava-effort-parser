package strava.effort.parser.mapper.activity

import java.time.Month

data class ActivityDistanceMonthAggregate(
    val month: Month,
    val year: Int,
    var miles: Float = 0.0f
)