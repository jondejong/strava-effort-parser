package strava.effort.parser

import strava.effort.parser.mapper.Mapper
import strava.effort.parser.mapper.week.RelativeEffortWeekMapper
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate

class Aggregator {

    val dataDirectoryName = "data"
    val dataFileSuffix = "csv"
    val activityDataFile = "activities"
    val mappers = ArrayList<Mapper>()

    init {
        // Add any custom mappers to this
        mappers.add(
            RelativeEffortWeekMapper()
        )
    }

    fun aggregate(startDate: String) {
        val dataDirectory = "${Paths.get("").toAbsolutePath().parent}/$dataDirectoryName"
        val data = "${dataDirectory}/${activityDataFile}.${dataFileSuffix}"

        val activities = CSVParser(File(data)).parse()
        mappers.forEach {
            val data = it.run(activities, LocalDate.parse("2015-06-29"))
            File("${dataDirectory}/${data.outputFileName}.${dataFileSuffix}").printWriter().use { out ->
                val headerBuffer = StringBuffer()
                for (headerToken in data.headers) {
                    headerBuffer.append("$headerToken,")
                }
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
}