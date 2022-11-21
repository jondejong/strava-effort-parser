package strava.effort.parser

import strava.effort.parser.mapper.Mapper
import strava.effort.parser.mapper.month.RelativeEffortMonthMapper
import strava.effort.parser.mapper.week.RelativeEffortWeekMapper
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate

class Aggregator {

    /**
     * To create new functionality, create a custom Mapper
     * by extending strava.effort.parser.mapper.Mapper and adding
     * an instance of the custom mapper here.
     */
    private val mappers = arrayOf<Mapper>(
        RelativeEffortWeekMapper(),
        RelativeEffortMonthMapper()
    )

    private val dataDirectoryName = "data"
    private val dataFileSuffix = "csv"
    private val activityDataFile = "activities"

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
}