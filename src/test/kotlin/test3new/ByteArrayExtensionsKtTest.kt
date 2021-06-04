package test3new

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.IllegalArgumentException

internal class ByteArrayExtensionsKtTest {
    @MethodSource("compressTests")
    @ParameterizedTest(name = "compress test {index}, {1}")
    fun compressTest(arrayToCompress: ByteArray, expectedArray: ByteArray) {
        assertArrayEquals(expectedArray, arrayToCompress.compress())
    }

    @MethodSource("decompressTests")
    @ParameterizedTest(name = "decompress test {index}, {1}")
    fun decompressTest(arrayToDecompress: ByteArray, expectedArray: ByteArray) {
        assertArrayEquals(expectedArray, arrayToDecompress.decompress())
    }

    @MethodSource("invalidArrayTests")
    @ParameterizedTest(name = "invalid array test {index}, {1}")
    fun invalidArrayTest(invalidArray: ByteArray) {
        assertThrows<IllegalArgumentException> { invalidArray.decompress() }
    }

    companion object {
        @JvmStatic
        fun compressTests(): List<Arguments> = listOf(
            Arguments.of(byteArrayOf(), byteArrayOf()),
            Arguments.of(byteArrayOf(1, 2, 3, 4, 5), byteArrayOf(1, 1, 1, 2, 1, 3, 1, 4, 1, 5)),
            Arguments.of(byteArrayOf(2, 4, 4, 4), byteArrayOf(1, 2, 3, 4)),
            Arguments.of(byteArrayOf(3, 3, 3, 5, 6, 6, 9), byteArrayOf(3, 3, 1, 5, 2, 6, 1, 9)),
            Arguments.of(byteArrayOf(-5, -5, -2, -2), byteArrayOf(2, -5, 2, -2)),
        )

        @JvmStatic
        fun decompressTests(): List<Arguments> = listOf(
            Arguments.of(byteArrayOf(), byteArrayOf()),
            Arguments.of(byteArrayOf(1, 5), byteArrayOf(5)),
            Arguments.of(byteArrayOf(1, 1, 1, 2, 1, 3), byteArrayOf(1, 2, 3)),
            Arguments.of(byteArrayOf(3, 3, 1, 5, 2, 6, 1, 9), byteArrayOf(3, 3, 3, 5, 6, 6, 9)),
            Arguments.of(byteArrayOf(3, -2, 1, 4), byteArrayOf(-2, -2, -2, 4))
        )

        @JvmStatic
        fun invalidArrayTests(): List<Arguments> = listOf(
            Arguments.of(byteArrayOf(1, 1, 1)),
            Arguments.of(byteArrayOf(3)),
            Arguments.of(byteArrayOf(-5, 2)),
            Arguments.of(byteArrayOf(0, 3))
        )
    }
}
