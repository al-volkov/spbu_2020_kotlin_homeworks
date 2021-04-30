package test2

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class WeatherData(
    @SerialName("coord") val coord: Coord,
    @SerialName("weather") val weather: List<Weather>,
    @SerialName("base") val base: String,
    @SerialName("main") val main: TemperatureData,
    @SerialName("visibility") val visibility: Int,
    @SerialName("wind") val wind: Wind,
    @SerialName("clouds") val clouds: Clouds,
    @SerialName("dt") val dt: Int,
    @SerialName("sys") val sys: Sys,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("cod") val cod: Int
)

@Serializable
data class Sys(
    @SerialName("type") val type: Int,
    @SerialName("id") val id: Int,
    @SerialName("country") val country: String,
    @SerialName("sunrise") val sunrise: Int,
    @SerialName("sunset") val sunset: Int
)

@Serializable
data class Coord(
    @SerialName("lon") val lon: Double,
    @SerialName("lat") val lat: Double
)

@Serializable
data class TemperatureData(
    @SerialName("temp") val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("pressure") val pressure: Int,
    @SerialName("humidity") val humidity: Int,
    @SerialName("temp_min") val tempMin: Double,
    @SerialName("temp_max") val tempMax: Double
)

@Serializable
data class Weather(
    @SerialName("id") val id: Int,
    @SerialName("main") val main: String,
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
)

@Serializable
data class Clouds(
    @SerialName("all") val all: Int
)

@Serializable
data class Wind(
    @SerialName("speed") val speed: Double,
    @SerialName("deg") val deg: Int
)

class WeatherDemonstrator {
    private val apiKey = File("api-keys.env").readText()
    fun getWeatherFromFile(fileName: String) {
        val fileNameFull = "src/main/resources/test2/$fileName"
        val file = File(fileNameFull)
        val cities = file.readLines()
        runBlocking {
            for (cityName in cities) {
                launch { getWeather(cityName) }
            }
        }
    }

    fun getWeather(cityName: String) {
        val request = "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$apiKey&units=metric"
        val result = sendGet(URL(request))
        val json = Json {
            ignoreUnknownKeys = true
        }
        val weather = json.decodeFromString<WeatherData>(result)
        println("$cityName weather:")
        println(weather)
    }

    private fun sendGet(url: URL): String {
        var result = ""
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            inputStream.bufferedReader().use {
                it.lines().forEach { line ->
                    result += line
                }
            }
        }
        return result
    }
}
