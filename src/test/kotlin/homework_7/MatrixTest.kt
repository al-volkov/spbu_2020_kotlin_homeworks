package homework_7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MatrixTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                Matrix(arrayOf(intArrayOf(5))),
                Matrix(arrayOf(intArrayOf(4))),
                Matrix(arrayOf(intArrayOf(20)))
            ),
            Arguments.of(
                Matrix(arrayOf(intArrayOf(1, 2), intArrayOf(3, 4))),
                Matrix(arrayOf(intArrayOf(5, 6), intArrayOf(7, 8))),
                Matrix(arrayOf(intArrayOf(19, 22), intArrayOf(43, 50)))
            ),
            Arguments.of(
                Matrix(arrayOf(intArrayOf(1, 2))),
                Matrix(arrayOf(intArrayOf(3), intArrayOf(4))),
                Matrix(arrayOf(intArrayOf(11)))
            ),
            Arguments.of(
                Matrix(arrayOf(intArrayOf(1, 2))),
                Matrix(arrayOf(intArrayOf(3, 4, 5, 6), intArrayOf(7, 8, 9, 10))),
                Matrix(arrayOf(intArrayOf(17, 20, 23, 26)))
            ),
            Arguments.of(
                Matrix(arrayOf(intArrayOf(1, 2, 3))),
                Matrix(arrayOf(intArrayOf(4, 5, 6), intArrayOf(7, 8, 9), intArrayOf(10, 11, 12))),
                Matrix(arrayOf(intArrayOf(48, 54, 60)))
            ),
            Arguments.of(
                Matrix(arrayOf(intArrayOf(1), intArrayOf(2), intArrayOf(3))),
                Matrix(arrayOf(intArrayOf(4, 5, 6))),
                Matrix(arrayOf(intArrayOf(4, 5, 6), intArrayOf(8, 10, 12), intArrayOf(12, 15, 18)))
            ),
            Arguments.of(
                Matrix(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6))),
                Matrix(arrayOf(intArrayOf(7, 8), intArrayOf(9, 10), intArrayOf(11, 12))),
                Matrix(arrayOf(intArrayOf(58, 64), intArrayOf(139, 154)))
            ),
            Arguments.of(
                Matrix(arrayOf(intArrayOf(1, 2), intArrayOf(3, 4), intArrayOf(5, 6))),
                Matrix(arrayOf(intArrayOf(7, 8, 9), intArrayOf(10, 11, 12))),
                Matrix(arrayOf(intArrayOf(27, 30, 33), intArrayOf(61, 68, 75), intArrayOf(95, 106, 117)))
            ),
            Arguments.of(
                Matrix(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9))),
                Matrix(arrayOf(intArrayOf(10, 11, 12), intArrayOf(13, 14, 15), intArrayOf(16, 17, 18))),
                Matrix(arrayOf(intArrayOf(84, 90, 96), intArrayOf(201, 216, 231), intArrayOf(318, 342, 366)))
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2, 3, 4),
                        intArrayOf(5, 6, 7, 8),
                        intArrayOf(9, 10, 11, 12),
                        intArrayOf(13, 14, 15, 16)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(17, 18, 19, 20),
                        intArrayOf(21, 22, 23, 24),
                        intArrayOf(25, 26, 27, 28),
                        intArrayOf(29, 30, 31, 32)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(250, 260, 270, 280),
                        intArrayOf(618, 644, 670, 696),
                        intArrayOf(986, 1028, 1070, 1112),
                        intArrayOf(1354, 1412, 1470, 1528)
                    )
                )
            ),

            Arguments.of(
                Matrix(arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9))),
                Matrix(arrayOf(intArrayOf(10, 11), intArrayOf(12, 13), intArrayOf(14, 15))),
                Matrix(arrayOf(intArrayOf(76, 82), intArrayOf(184, 199), intArrayOf(292, 316)))
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2, 3, 4, 5),
                        intArrayOf(6, 7, 8, 9, 10),
                        intArrayOf(11, 12, 13, 14, 15),
                        intArrayOf(16, 17, 18, 19, 20),
                        intArrayOf(21, 22, 23, 24, 25)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(26, 27, 28, 29, 30),
                        intArrayOf(31, 32, 33, 34, 35),
                        intArrayOf(36, 37, 38, 39, 40),
                        intArrayOf(41, 42, 43, 44, 45),
                        intArrayOf(46, 47, 48, 49, 50)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(590, 605, 620, 635, 650),
                        intArrayOf(1490, 1530, 1570, 1610, 1650),
                        intArrayOf(2390, 2455, 2520, 2585, 2650),
                        intArrayOf(3290, 3380, 3470, 3560, 3650),
                        intArrayOf(4190, 4305, 4420, 4535, 4650)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2, 3, 4, 5, 6),
                        intArrayOf(7, 8, 9, 10, 11, 12),
                        intArrayOf(13, 14, 15, 16, 17, 18),
                        intArrayOf(19, 20, 21, 22, 23, 24),
                        intArrayOf(25, 26, 27, 28, 29, 30),
                        intArrayOf(31, 32, 33, 34, 35, 36)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(37, 38, 39, 40, 41, 42),
                        intArrayOf(43, 44, 45, 46, 47, 48),
                        intArrayOf(49, 50, 51, 52, 53, 54),
                        intArrayOf(55, 56, 57, 58, 59, 60),
                        intArrayOf(61, 62, 63, 64, 65, 66),
                        intArrayOf(67, 68, 69, 70, 71, 72)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(1197, 1218, 1239, 1260, 1281, 1302),
                        intArrayOf(3069, 3126, 3183, 3240, 3297, 3354),
                        intArrayOf(4941, 5034, 5127, 5220, 5313, 5406),
                        intArrayOf(6813, 6942, 7071, 7200, 7329, 7458),
                        intArrayOf(8685, 8850, 9015, 9180, 9345, 9510),
                        intArrayOf(10557, 10758, 10959, 11160, 11361, 11562)
                    )
                )
            )
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "multiplyMatrix test {index}, {1}")
    fun multiplyMatrixTest(firstMatrix: Matrix, secondMatrix: Matrix, expectedMatrix: Matrix) {
        assertEquals(expectedMatrix, firstMatrix * secondMatrix)
    }
}
