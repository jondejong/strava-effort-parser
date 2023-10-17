/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package strava.effort.parser

import strava.effort.parser.mapper.Mapper
import strava.effort.parser.mapper.activity.biking.BikingTotalMonthMapper
import strava.effort.parser.mapper.activity.running.month.RunningTotalMonthMapper
import strava.effort.parser.mapper.month.RelativeEffortMonthMapper
import strava.effort.parser.mapper.week.RelativeEffortWeekMapper

class App {
    /**
     * To create new functionality, create a custom Mapper
     * by extending strava.effort.parser.mapper.Mapper and adding
     * an instance of the custom mapper here.
     */
    private val mappers = arrayOf<Mapper>(
        RelativeEffortWeekMapper(),
        RelativeEffortMonthMapper(),
        RunningTotalMonthMapper(),
        BikingTotalMonthMapper()
    )

    private val aggregator: Aggregator = Aggregator()
    fun run() {
        aggregator.aggregate(mappers)
    }
}

fun main() {
    App().run()
}
