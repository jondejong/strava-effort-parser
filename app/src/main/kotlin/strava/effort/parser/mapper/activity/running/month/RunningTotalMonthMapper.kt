package strava.effort.parser.mapper.activity.running.month

import strava.effort.parser.Activity
import strava.effort.parser.ActivityType
import strava.effort.parser.mapper.Mapper
import strava.effort.parser.mapper.activity.ActivityDistanceMonthAggregate
import strava.effort.parser.mapper.activity.MonthAggregator
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.*

class RunningTotalMonthMapper : MonthAggregator(ActivityType.RUN)

