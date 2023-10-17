package strava.effort.parser

import org.apache.commons.csv.CSVFormat
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CSVParser(private val file: File) {
    //Jul 2, 2015, 11:49:58 PM
    private val format = "MMM d, yyyy, h:mm:ss a"
    private val formatter = DateTimeFormatter.ofPattern(format)

    fun parse(): List<Activity> {
        return CSVFormat.Builder.create(CSVFormat.DEFAULT).apply {
            setIgnoreSurroundingSpaces(true)
        }.build().parse(file.inputStream().reader())
            .drop(1) // Dropping the header
            .map {
                val type = when (it[3]) {
                    "Run" -> ActivityType.RUN
                    "Ride" -> ActivityType.BIKE
                    "Virtual Ride" -> ActivityType.BIKE
                    else -> ActivityType.OTHER
                }
                Activity(
                    id = it[0].toLong(),
                    time = LocalDateTime.parse(it[1], formatter),
                    type = type,
                    name = it[2],
                    description = it[4],
                    effort = if (it[8].isEmpty()) 0 else Integer.parseInt(it[8]),
                    distance = if (it[6].isEmpty()) 0.0f else it[6].toFloat(),
                )
            }
    }
}