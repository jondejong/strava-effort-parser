package strava.effort.parser.mapper

data class MapperOutput(
    val outputFileName: String,
    val headers: List<String>,
    val data: Collection<List<String>>
)
