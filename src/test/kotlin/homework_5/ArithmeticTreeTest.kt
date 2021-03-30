package homework_5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ArithmeticTreeTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of("test1.txt", 4),
            Arguments.of("test2.txt", 2),
            Arguments.of("test3.txt", -2)
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test {index}, {1}")
    fun arithmeticTree(path: String, expectedValue: Int) {
        val tree = ArithmeticTree(ArithmeticTreeTest::class.java.getResource(path).path)
        assertEquals(ArithmeticTreeTest::class.java.getResource(path).readText(), tree.toString())
        assertEquals(expectedValue, tree.getValue())
    }
}
