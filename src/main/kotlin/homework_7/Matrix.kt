package homework_7

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException

class Matrix(val numberOfRows: Int, val numberOfColumns: Int) {

    constructor(array: Array<IntArray>) : this(array.size, array[0].size) {
        for (row in 0 until this.numberOfRows) {
            require(array[row].size == this.numberOfColumns) {
                throw IllegalArgumentException("this array cannot be interpreted as a matrix")
            }
            for (column in 0 until this.numberOfColumns) {
                this[row, column] = array[row][column]
            }
        }
    }

    class SubMatrix(
        private val mainMatrix: Matrix,
        private val startRow: Int,
        private val finalRow: Int,
        private val startColumn: Int,
        private val finalColumn: Int
    ) {
        companion object {
            suspend fun recursiveLaunch(
                firstMatrixPartition: Array<Array<SubMatrix>>,
                secondMatrixPartition: Array<Array<SubMatrix>>,
                resultMatrixPartition: Array<Array<SubMatrix>>,
                temporaryMatrixPartition: Array<Array<SubMatrix>>
            ) {
                coroutineScope {
                    val listOfCoroutines = mutableListOf<Job>()
                    listOfCoroutines.add(launch {
                        firstMatrixPartition[0][0].multiplySubMatrix(
                            secondMatrixPartition[0][0],
                            resultMatrixPartition[0][0]
                        )
                    })
                    listOfCoroutines.add(launch {
                        firstMatrixPartition[0][0].multiplySubMatrix(
                            secondMatrixPartition[0][1],
                            resultMatrixPartition[0][1]
                        )
                    })
                    listOfCoroutines.add(launch {
                        firstMatrixPartition[1][0].multiplySubMatrix(
                            secondMatrixPartition[0][0],
                            resultMatrixPartition[1][0]
                        )
                    })
                    listOfCoroutines.add(launch {
                        firstMatrixPartition[1][0].multiplySubMatrix(
                            secondMatrixPartition[0][1],
                            resultMatrixPartition[1][1]
                        )
                    })
                    listOfCoroutines.add(launch {
                        firstMatrixPartition[0][1].multiplySubMatrix(
                            secondMatrixPartition[1][0],
                            temporaryMatrixPartition[0][0]
                        )
                    })
                    listOfCoroutines.add(launch {
                        firstMatrixPartition[0][1].multiplySubMatrix(
                            secondMatrixPartition[1][1],
                            temporaryMatrixPartition[0][1]
                        )
                    })
                    listOfCoroutines.add(launch {
                        firstMatrixPartition[1][1].multiplySubMatrix(
                            secondMatrixPartition[1][0],
                            temporaryMatrixPartition[1][0]
                        )
                    })
                    listOfCoroutines.add(launch {
                        firstMatrixPartition[1][1].multiplySubMatrix(
                            secondMatrixPartition[1][1],
                            temporaryMatrixPartition[1][1]
                        )
                    })
                    listOfCoroutines.forEach { it.join() }
                }
            }
        }

        private val numberOfRows = finalRow - startRow + 1
        private val numberOfColumns = finalColumn - startColumn + 1

        operator fun plusAssign(secondMatrix: SubMatrix) {
            for (i in startRow..finalRow) {
                for (j in startColumn..finalColumn) {
                    this.mainMatrix.array[i][j] +=
                        secondMatrix.mainMatrix.array[
                                i - startRow + secondMatrix.startRow][j - startColumn + secondMatrix.startColumn
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
                    recursiveLaunch(
                        thisMatrixPartition,
                        secondMatrixPartition,
                        resultMatrixPartition,
                        temporaryMatrixPartition
                    )
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

    private val array = Array(numberOfRows) { IntArray(numberOfColumns) { 0 } }

    init {
        require(numberOfColumns >= 1 && numberOfRows >= 1) {
            throw IllegalArgumentException("size of matrix must be positive integer")
        }
    }

    operator fun get(i: Int, j: Int) = array[i][j]

    operator fun set(i: Int, j: Int, value: Int) = run { array[i][j] = value }

    private fun fullSubMatrix(): SubMatrix = SubMatrix(this, 0, numberOfRows - 1, 0, numberOfColumns - 1)

    operator fun times(secondMatrix: Matrix): Matrix {
        require(this.numberOfColumns == secondMatrix.numberOfRows) {
            throw IllegalArgumentException("these matrices cannot be multiplied")
        }
        val resultMatrix = Matrix(this.numberOfRows, secondMatrix.numberOfColumns)
        val matrix = this.fullSubMatrix()
        runBlocking {
            launch {
                matrix.multiplySubMatrix(
                    secondMatrix.fullSubMatrix(),
                    resultMatrix.fullSubMatrix()
                )
            }
        }
        return resultMatrix
    }

    override fun toString(): String {
        val listOfRows = mutableListOf<String>()
        for (row in 0 until numberOfRows) {
            listOfRows += array[row].joinToString(" ")
        }
        return listOfRows.joinToString("\n")
    }

    override operator fun equals(other: Any?): Boolean {
        if (other !is Matrix ||
            other.numberOfRows != this.numberOfRows ||
            other.numberOfColumns != this.numberOfColumns
        ) {
            return false
        }
        var result = true
        for (row in 0 until numberOfRows) {
            for (column in 0 until numberOfColumns) {
                if (this[row, column] != other[row, column]) {
                    result = false
                }
            }
        }
        return result
    }
}
