package strava.effort.parser.mapper.month

import strava.effort.parser.Activity
import strava.effort.parser.mapper.Mapper
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.*

class RelativeEffortMonthMapper : Mapper(
    headerValues = listOf("Month", "Year", "Effort"),
    outputFileName = "relativeEffortByMonth"
) {

    override fun map(activities: List<Activity>, startDate: LocalDate): Collection<List<String>> {
        var done = false
        val now = LocalDate.now()
        val months = ArrayList<MonthAggregate>()
        var month = startDate.month
        var year = startDate.year
        do {
            months.add(
                MonthAggregate(
                    month = month,
                    year = year,
                    effort = 0
                )
            )
            month = month.plus(1)
            if (month == Month.JANUARY) {
                year++
            }
            if (year > now.year) {
                done = true
            }
            if (year == now.year && month > now.month) {
                done = true
            }
        } while (!done)

        for (activity in activities) {
            for (month in months) {
                if (activity.time.month == month.month && activity.time.year == month.year) {
                    month.effort += activity.effort
                }
            }
        }

        return months.map {
            listOf("${it.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)},${it.year},${it.effort}")
        }
    }
}