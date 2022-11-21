package strava.effort.parser.mapper

import strava.effort.parser.Activity
import java.time.LocalDate

abstract class Mapper(
    val headerValues: List<String>,
    val outputFileName: String
) {

    fun run(activities: List<Activity>, startDate: LocalDate): MapperOutput {
        return MapperOutput(
            outputFileName = outputFileName,
            headers = headerValues,
            data = this.map(activities, startDate)
        )
    }

    abstract fun map(activities: List<Activity>, startDate: LocalDate): Collection<List<String>>
}