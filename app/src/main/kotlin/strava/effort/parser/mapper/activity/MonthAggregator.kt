package strava.effort.parser.mapper.activity

import strava.effort.parser.Activity
import strava.effort.parser.ActivityType
import strava.effort.parser.mapper.Mapper
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.*

abstract class MonthAggregator : Mapper {

    private var activityType: ActivityType

    constructor(activityType: ActivityType) : super(
        headerValues = listOf("Month", "Year", "Miles"),
        outputFileName = "${activityType}-total-month"
    ) {
        this.activityType = activityType
    }

    override fun map(activities: List<Activity>, startDate: LocalDate): Collection<List<String>> {
        var done = false
        val now = LocalDate.now()
        val months = ArrayList<ActivityDistanceMonthAggregate>()
        var month = startDate.month
        var year = startDate.year
        do {
            months.add(
                ActivityDistanceMonthAggregate(
                    month = month,
                    year = year,
                    miles = 0.0f
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
            if (activity.type == this.activityType) {
                for (month in months) {
                    if (activity.time.month == month.month && activity.time.year == month.year) {
                        month.miles += convertKilometersToMiles(activity.distance)
                    }
                }
            }
        }

        return months.map {
            listOf("${it.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)},${it.year},${it.miles}")
        }
    }
}