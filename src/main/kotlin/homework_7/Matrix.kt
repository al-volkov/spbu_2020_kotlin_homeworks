@file:Suppress("LongMethod") // temporary
package homework_7

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException

class Matrix(val numberOfRows: Int, val numberOfColumns: Int) {
    class SubMatrix(
        val mainMatrix: Matrix,
        val startRow: Int,
        val finalRow: Int,
        val startColumn: Int,
        val finalColumn: Int
    ) {
        private val numberOfRows: Int
            get() = finalRow - startRow + 1
        private val numberOfColumns: Int
            get() = finalColumn - startColumn + 1

        operator fun plusAssign(secondMatrix: SubMatrix) {
            for (i in startRow..finalRow) {
                for (j in startColumn..finalColumn) {
                    this.mainMatrix.array[i][j] +=
                        secondMatrix.mainMatrix.array[
                                i - startRow + secondMatrix.startRow][j - startColumn + secondMatrix.startRow
                        ]
                }
            }
        }

        private fun oneRowMatrixMultiply(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
            for (i in startColumn..finalColumn) {
                for (j in secondMatrix.startColumn..secondMatrix.finalColumn)
                    resultMatrix.mainMatrix[
                            resultMatrix.startRow, resultMatrix.startColumn + j - secondMatrix.startColumn
                    ] +=
                        mainMatrix[startRow, i] * secondMatrix.mainMatrix[
                                i - startColumn + secondMatrix.startRow, j
                        ]
            }
        }

        private fun oneColumnMatrixMultiply(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
            for (i in startRow..finalRow) {
                for (j in secondMatrix.startColumn..secondMatrix.finalColumn) {
                    resultMatrix.mainMatrix[
                            i - startRow + resultMatrix.startRow,
                            j - secondMatrix.startColumn + resultMatrix.startColumn
                    ] +=
                        this.mainMatrix[i, this.startColumn] * secondMatrix.mainMatrix[secondMatrix.startRow, j]
                }
            }
        }

        private fun secondMatrixOneColumnMultiply(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
            for (i in startRow..finalRow) {
                for (j in startColumn..finalColumn) {
                    resultMatrix.mainMatrix[
                            i - startRow + resultMatrix.startRow, resultMatrix.startColumn
                    ] += mainMatrix[i, j] *
                            secondMatrix.mainMatrix[
                                    j - startColumn + secondMatrix.startRow, secondMatrix.startColumn
                            ]
                }
            }
        }

        suspend fun multiplySubMatrix(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
            when {
                this.numberOfRows == 1 -> {
                    this.oneRowMatrixMultiply(secondMatrix, resultMatrix)
                    return
                }
                this.numberOfColumns == 1 -> {
                    this.oneColumnMatrixMultiply(secondMatrix, resultMatrix)
                    return
                }
                secondMatrix.numberOfColumns == 1 -> {
                    this.secondMatrixOneColumnMultiply(secondMatrix, resultMatrix)
                }
                else -> {
                    val temporaryMatrix =
                        Matrix(resultMatrix.numberOfRows, resultMatrix.numberOfColumns).fullSubMatrix()
                    val thisMatrixPartition = this.partition()
                    val secondMatrixPartition = secondMatrix.partition()
                    val resultMatrixPartition = resultMatrix.partition()
                    val temporaryMatrixPartition = temporaryMatrix.partition()
                    coroutineScope {
                        val coroutine1 = launch {
                            thisMatrixPartition[0][0].multiplySubMatrix(
                                secondMatrixPartition[0][0],
                                resultMatrixPartition[0][0]
                            )
                        }
                        val coroutine2 = launch {
                            thisMatrixPartition[0][0].multiplySubMatrix(
                                secondMatrixPartition[0][1],
                                resultMatrixPartition[0][1]
                            )
                        }
                        val coroutine3 = launch {
                            thisMatrixPartition[1][0].multiplySubMatrix(
                                secondMatrixPartition[0][0],
                                resultMatrixPartition[1][0]
                            )
                        }
                        val coroutine4 = launch {
                            thisMatrixPartition[1][0].multiplySubMatrix(
                                secondMatrixPartition[0][1],
                                resultMatrixPartition[1][1]
                            )
                        }
                        val coroutine5 = launch {
                            thisMatrixPartition[0][1].multiplySubMatrix(
                                secondMatrixPartition[1][0],
                                temporaryMatrixPartition[0][0]
                            )
                        }
                        val coroutine6 = launch {
                            thisMatrixPartition[0][1].multiplySubMatrix(
                                secondMatrixPartition[1][1],
                                temporaryMatrixPartition[0][1]
                            )
                        }
                        val coroutine7 = launch {
                            thisMatrixPartition[1][1].multiplySubMatrix(
                                secondMatrixPartition[1][0],
                                temporaryMatrixPartition[1][0]
                            )
                        }
                        val coroutine8 = launch {
                            thisMatrixPartition[1][1].multiplySubMatrix(
                                secondMatrixPartition[1][1],
                                temporaryMatrixPartition[1][1]
                            )
                        }
                        val listOfCoroutines = listOf(
                            coroutine1,
                            coroutine2,
                            coroutine3,
                            coroutine4,
                            coroutine5,
                            coroutine6,
                            coroutine7,
                            coroutine8
                        )
                        listOfCoroutines.forEach { it.join() }
                    }
                    resultMatrix += temporaryMatrix
                }
            }
        }

        private fun partition(): Array<Array<SubMatrix>> {
            val firstListOfSubMatrices = mutableListOf<SubMatrix>()
            val secondListOfSubMatrices = mutableListOf<SubMatrix>()
            val midRow = (startRow + finalRow - 1) / 2
            val midColumn = (startColumn + finalColumn - 1) / 2
            firstListOfSubMatrices.add(SubMatrix(mainMatrix, startRow, midRow, startColumn, midColumn))
            firstListOfSubMatrices.add(SubMatrix(mainMatrix, startRow, midRow, midColumn + 1, finalColumn))
            secondListOfSubMatrices.add(SubMatrix(mainMatrix, midRow + 1, finalRow, startColumn, midColumn))
            secondListOfSubMatrices.add(SubMatrix(mainMatrix, midRow + 1, finalRow, midColumn + 1, finalColumn))
            return arrayOf(firstListOfSubMatrices.toTypedArray(), secondListOfSubMatrices.toTypedArray())
        }
    }

    constructor(array: Array<IntArray>) : this(array.size, array[0].size) {
        for (row in array) {
            require(row.size == this.numberOfColumns) {
                throw IllegalArgumentException("this array cannot be interpreted as a matrix")
            }
        }
        for (i in 0 until this.numberOfRows) {
            for (j in 0 until this.numberOfColumns) {
                this[i, j] = array[i][j]
            }
        }
    }

    private val array = Array(numberOfRows) { IntArray(numberOfColumns) { 0 } }

    init {
        require(numberOfColumns >= 1 && numberOfRows >= 1) {
            throw IllegalArgumentException("size of matrix must be positive integer")
        }
    }

    operator fun get(i: Int, j: Int) = array[i][j]
    operator fun set(i: Int, j: Int, value: Int) = run { array[i][j] = value }

    private fun fullSubMatrix(): SubMatrix = SubMatrix(this, 0, numberOfRows - 1, 0, numberOfColumns - 1)
    fun multiplyMatrix(secondMatrix: Matrix, resultMatrix: Matrix) {
        require(this.numberOfColumns == secondMatrix.numberOfRows) {
            throw IllegalArgumentException("these matrices cannot be multiplied")
        }
        val matrix = this
        runBlocking {
            launch {
                matrix.fullSubMatrix().multiplySubMatrix(
                    secondMatrix.fullSubMatrix(),
                    resultMatrix.fullSubMatrix()
                )
            }
        }
    }

    override fun toString(): String {
        var string = ""
        for (i in 0 until numberOfRows - 1) {
            for (j in 0 until numberOfColumns) {
                string += this[i, j]
                string += " "
            }
            string += "\n"
        }
        for (i in 0 until numberOfColumns) {
            string += this[numberOfRows - 1, i]
            string += " "
        }
        return string
    }

    override operator fun equals(other: Any?): Boolean = this.toString() == other.toString()
    override fun hashCode(): Int {
        var result = numberOfRows
        result = 31 * result + numberOfColumns
        result = 31 * result + array.contentDeepHashCode()
        return result
    }
}
