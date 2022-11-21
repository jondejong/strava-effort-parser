package strava.effort.parser

import java.time.LocalDateTime

data class Activity(
    val id: Long,
    val time: LocalDateTime,
    val type: ActivityType,
    val name: String,
    val description: String,
    val effort: Int = 0
)
