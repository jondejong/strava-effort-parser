package strava.effort.parser

import strava.effort.parser.mapper.Mapper
import java.io.File
import java.nio.file.Paths
import java.time.DayOfWeek
import java.time.LocalDate

class Aggregator {

    private val dataDirectoryName = "data"
    private val dataFileSuffix = "csv"
    private val activityDataFile = "activities"

    fun aggregate(mappers: Array<Mapper>) {
        val dataDirectory = "${Paths.get("").toAbsolutePath().parent}/$dataDirectoryName"
        val data = "${dataDirectory}/${activityDataFile}.${dataFileSuffix}"

        val activities = CSVParser(File(data)).parse()

        val startDate = activities.fold(toMonday(LocalDate.now()), fun(min, activity): LocalDate {
            return if (activity.time < min.atStartOfDay()) {
                toMonday(activity.time.toLocalDate())
            } else {
                min
            }
        })

        mappers.forEach {
            val data = it.run(activities, startDate)
            File("${dataDirectory}/${data.outputFileName}.${dataFileSuffix}").printWriter().use { out ->
                val headerBuffer = StringBuffer()
                for (headerToken in data.headers) {
                    headerBuffer.append("$headerToken,")
                }
                out.println(headerBuffer.toString())

                data.data.forEach { dataLine ->
                    val lineBuffer = StringBuffer()
                    for (dataToken in dataLine) {
                        lineBuffer.append("$dataToken,")
                    }
                    out.println(lineBuffer.toString())
                }
            }
        }
    }

    private fun toMonday(from: LocalDate): LocalDate {
        var checkDate = from
        do {
            if (checkDate.dayOfWeek.equals(DayOfWeek.MONDAY)) {
                return checkDate
            }
            checkDate = checkDate.minusDays(1)
        } while (true)
    }
}