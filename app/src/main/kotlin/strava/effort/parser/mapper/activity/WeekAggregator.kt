package strava.effort.parser.mapper.activity

import strava.effort.parser.Activity
import strava.effort.parser.ActivityType
import strava.effort.parser.mapper.Mapper
import java.time.LocalDate

abstract class WeekAggregator : Mapper {

    private var activityType: ActivityType

    constructor(activityType: ActivityType) : super(
        headerValues = listOf("Week Start", "Week End", "Miles"),
        outputFileName = "${activityType}-total-week"
    ) {
        this.activityType = activityType
    }

    override fun map(activities: List<Activity>, startDate: LocalDate): Collection<List<String>> {
        var done = false
        val now = LocalDate.now()
        val weeks = ArrayList<ActivityDistanceWeekAggregate>()
        var weekStart = startDate
        do {
            val end = weekStart.plusDays(6)
            weeks.add(
                ActivityDistanceWeekAggregate(
                    start = weekStart,
                    end = end
                )
            )
            weekStart = end.plusDays(1)
            done = end >= now
        } while (!done)

        for (activity in activities) {
            if (activity.type == this.activityType) {
                for (week in weeks) {
                    if (activity.time >= week.start.atStartOfDay() && activity.time < week.end.plusDays(1)
                            .atStartOfDay()
                    ) {
                        week.miles += convertKilometersToMiles(activity.distance)
                    }
                }
            }
        }

        return weeks.map {
            listOf("${it.start},${it.end},${it.miles}")
        }
    }

}