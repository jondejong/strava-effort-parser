package strava.effort.parser

import strava.effort.parser.mapper.Mapper
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate

class Aggregator {

    private val dataDirectoryName = "data"
    private val dataFileSuffix = "csv"
    private val activityDataFile = "activities"

    fun aggregate(mappers: Array<Mapper>, startDate: String) {
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