package homework_3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.opentest4j.AssertionFailedError
import java.io.File

internal class Task_3KtTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                File("src/test/resources/expectedResults/test1.kt").readText(),
                generateFileForTests("src/test/resources/testСonfigs/testconfig1.yaml").toString()
            ),
            Arguments.of(
                File("src/test/resources/expectedResults/test2.kt").readText(),
                generateFileForTests("src/test/resources/testСonfigs/testconfig2.yaml").toString()
            )
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test {index}, {1}")
    fun generateFileForTests(expected: String, input: String) {
        assertEquals(expected.replace("\r", ""), input.replace("\r", ""))
    }
}
