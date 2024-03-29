package homework_3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class Task3KtTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of("test1"),
            Arguments.of("test2")
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test {index}, {1}")
    fun generateFileForTests(folderName: String) {
        val expectedFile = Task3KtTest::class.java.getResource("$folderName/expected.kt").readText().replace("\r", "")
        val pathToConfig = Task3KtTest::class.java.getResource("$folderName/config.yaml").path
        val generatedFile = TestGenerator(pathToConfig).getString()
        assertEquals(expectedFile, generatedFile)
    }
}
