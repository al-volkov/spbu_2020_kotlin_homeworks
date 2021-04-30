package test2

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class WeatherDemonstratorTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of("Moscow"),
            Arguments.of("Petersburg"),
            Arguments.of("Boon")
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test {index}, {1}")
    fun getWeatherTest(cityName: String) {
        assertDoesNotThrow { WeatherDemonstrator().getWeather(cityName) }
    }
}
