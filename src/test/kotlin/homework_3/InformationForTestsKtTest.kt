package homework_3

import com.squareup.kotlinpoet.FileSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.nio.file.Path

internal class InformationForTestsKtTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                File("src/test/resources/expectedResults/test1.kt").readText(),
                generateFileForTests("src/test/resources/testСonfigs/testconfig1.yaml")
            ),
            Arguments.of(
                File("src/test/resources/expectedResults/test2.kt").readText(),
                generateFileForTests("src/test/resources/testСonfigs/testconfig2.yaml")
            )
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test {index}, {1}")
    fun generateFileForTests(expected: String, input: FileSpec) {
        assertEquals(expected, input.toString())
    }
}
