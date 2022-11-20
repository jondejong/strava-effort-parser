package strava.effort.parser.mapper.month

import java.time.Month
import java.time.Year

data class MonthAggregate(val month: Month, val year: Year, val effort: Int = 0)
