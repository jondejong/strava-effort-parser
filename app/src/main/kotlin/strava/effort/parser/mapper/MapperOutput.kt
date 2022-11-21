package strava.effort.parser.mapper

import java.io.File

data class MapperOutput(
    val outputFileName: String,
    val headers: List<String>,
    val data: Collection<List<String>>
)
