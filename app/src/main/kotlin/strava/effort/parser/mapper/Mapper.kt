package strava.effort.parser.mapper

import strava.effort.parser.Activity
import java.time.LocalDate

abstract class Mapper(
    val headerValues: List<String>,
    val outputFileName: String
) {

    companion object Constants {
        const val KM_PER_MILE = 1.60934f
    }

    fun run(activities: List<Activity>, startDate: LocalDate): MapperOutput {
        return MapperOutput(
            outputFileName = outputFileName,
            headers = headerValues,
            data = this.map(activities, startDate)
        )
    }

    protected fun convertKilometersToMiles(kilometers: Float): Float {
        return kilometers / KM_PER_MILE
    }

    abstract fun map(activities: List<Activity>, startDate: LocalDate): Collection<List<String>>
}