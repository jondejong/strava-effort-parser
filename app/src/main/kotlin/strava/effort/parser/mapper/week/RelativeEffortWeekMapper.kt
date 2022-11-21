package strava.effort.parser.mapper.week

import strava.effort.parser.Activity
import strava.effort.parser.mapper.Mapper
import java.time.LocalDate

class RelativeEffortWeekMapper : Mapper(
    headerValues = listOf("Week Start", "Week End", "Effort"),
    outputFileName = "relativeEffortByWeek"
) {

    override fun map(activities: List<Activity>, startDate: LocalDate): Collection<List<String>> {
        var done = false
        val now = LocalDate.now()
        val weeks = ArrayList<WeekAggregate>()
        var weekStart = startDate
        do {
            val end = weekStart.plusDays(6)
            weeks.add(
                WeekAggregate(
                    start = weekStart,
                    end = end
                )
            )
            weekStart = end.plusDays(1)
            done = end >= now
        } while (!done)

        for (activity in activities) {
            for (week in weeks) {
                if (activity.time >= week.start.atStartOfDay() && activity.time < week.end.plusDays(1).atStartOfDay()) {
                    week.effort += activity.effort
                }
            }
        }

        return weeks.map {
            listOf("${it.start},${it.end},${it.effort}")
        }
    }
}