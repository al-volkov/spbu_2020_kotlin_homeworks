package homework_7

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Matrix(private val numberOfRows: Int, private val numberOfColumns: Int) {
    init {
        require(numberOfColumns >= 1 && numberOfRows >= 1) { "size of matrix must be positive integer" }
    }

    private val array = Array(numberOfRows) { IntArray(numberOfColumns) { 0 } }
    private val rowRange = 0 until numberOfRows
    private val columnRange = 0 until numberOfColumns

    constructor(array: Array<IntArray>) : this(array.size, array.first().size) {
        for (row in 0 until this.numberOfRows) {
            require(array[row].size == this.numberOfColumns) { "this array cannot be interpreted as a matrix" }
            for (column in 0 until this.numberOfColumns) {
                this[row, column] = array[row][column]
            }
        }
    }

    operator fun get(i: Int, j: Int) = array[i][j]

    operator fun set(i: Int, j: Int, value: Int) = run { array[i][j] = value }

    private class SubMatrix(
        private val mainMatrix: Matrix,
        private val startRow: Int,
        private val finalRow: Int,
        private val startColumn: Int,
        private val finalColumn: Int
    ) {
        private val numberOfRows = finalRow - startRow + 1
        private val numberOfColumns = finalColumn - startColumn + 1
        private val rowRange = startRow..finalRow
        private val columnRange = startColumn..finalColumn

        operator fun plusAssign(secondMatrix: SubMatrix) {
            for ((row1, row2) in rowRange.zip(secondMatrix.rowRange)) {
                for ((column1, column2) in columnRange.zip(secondMatrix.columnRange)) {
                    this.mainMatrix[row1, column1] += secondMatrix.mainMatrix[row2, column2]
                }
            }
        }

        private fun oneRowMatrixMultiply(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
            val secondMatrixRowRange = columnRange.map { it - startColumn + secondMatrix.startRow }
            val resultMatrixColumnRange = secondMatrix.columnRange.map {
                it + -secondMatrix.startColumn + resultMatrix.startColumn
            }
            for ((thisMatrixColumn, secondMatrixRow) in columnRange.zip(secondMatrixRowRange)) {
                for ((secondMatrixColumn, resultMatrixColumn) in secondMatrix.columnRange.zip(
                    resultMatrixColumnRange
                )) {
                    resultMatrix.mainMatrix[
                            resultMatrix.startRow, resultMatrixColumn
                    ] +=
                        mainMatrix[startRow, thisMatrixColumn] * secondMatrix.mainMatrix[
                                secondMatrixRow, secondMatrixColumn
                        ]
                }
            }
        }

        private fun oneColumnMatrixMultiply(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
            val resultMatrixRowRange = rowRange.map { it - startRow + resultMatrix.startRow }
            val resultMatrixColumnRange =
                secondMatrix.columnRange.map { it - secondMatrix.startColumn + resultMatrix.startColumn }
            for ((thisMatrixRow, resultMatrixRow) in rowRange.zip(resultMatrixRowRange)) {
                for ((secondMatrixColumn, resultMatrixColumn) in secondMatrix.columnRange.zip(
                    resultMatrixColumnRange
                )) {
                    resultMatrix.mainMatrix[resultMatrixRow, resultMatrixColumn] +=
                        this.mainMatrix[thisMatrixRow, startColumn] *
                                secondMatrix.mainMatrix[secondMatrix.startRow, secondMatrixColumn]
                }
            }
        }

        private fun secondMatrixOneColumnMultiply(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
            val resultMatrixRowRange = rowRange.map { it - startRow + resultMatrix.startRow }
            val secondMatrixRowRange = columnRange.map { it - startColumn + secondMatrix.startRow }
            for ((thisMatrixRow, resultMatrixRow) in rowRange.zip(resultMatrixRowRange)) {
                for ((thisMatrixColumn, secondMatrixRow) in columnRange.zip(secondMatrixRowRange)) {
                    resultMatrix.mainMatrix[
                            resultMatrixRow, resultMatrix.startColumn
                    ] += mainMatrix[thisMatrixRow, thisMatrixColumn] *
                            secondMatrix.mainMatrix[
                                    secondMatrixRow, secondMatrix.startColumn
                            ]
                }
            }
        }

        fun multiplySubMatrix(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
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
            val midRow = (startRow + finalRow - 1) / 2
            val midColumn = (startColumn + finalColumn - 1) / 2
            val submatrix11 = SubMatrix(mainMatrix, startRow, midRow, startColumn, midColumn)
            val submatrix12 = SubMatrix(mainMatrix, startRow, midRow, midColumn + 1, finalColumn)
            val submatrix21 = SubMatrix(mainMatrix, midRow + 1, finalRow, startColumn, midColumn)
            val submatrix22 = SubMatrix(mainMatrix, midRow + 1, finalRow, midColumn + 1, finalColumn)
            return arrayOf(arrayOf(submatrix11, submatrix12), arrayOf(submatrix21, submatrix22))
        }

        companion object {
            private data class MultiplicationTemplate(
                val firstArrayIndex1: Int,
                val firstArrayIndex2: Int,
                val secondArrayIndex1: Int,
                val secondArrayIndex2: Int,
                val resultMatrixIndex1: Int,
                val resultMatrixIndex2: Int
            )

            fun recursiveLaunch(
                firstMatrixPartition: Array<Array<SubMatrix>>,
                secondMatrixPartition: Array<Array<SubMatrix>>,
                resultMatrixPartition: Array<Array<SubMatrix>>,
                temporaryMatrixPartition: Array<Array<SubMatrix>>
            ) {
                runBlocking {
                    val listOfTemplates = mutableListOf<MultiplicationTemplate>()
                    listOfTemplates += MultiplicationTemplate(0, 0, 0, 0, 0, 0)
                    listOfTemplates += MultiplicationTemplate(0, 0, 0, 1, 0, 1)
                    listOfTemplates += MultiplicationTemplate(1, 0, 0, 0, 1, 0)
                    listOfTemplates += MultiplicationTemplate(1, 0, 0, 1, 1, 1)
                    listOfTemplates.forEach {
                        launch {
                            firstMatrixPartition[it.firstArrayIndex1][it.firstArrayIndex2].multiplySubMatrix(
                                secondMatrixPartition[it.secondArrayIndex1][it.secondArrayIndex2],
                                resultMatrixPartition[it.resultMatrixIndex1][it.resultMatrixIndex2]
                            )
                        }
                    }
                    listOfTemplates.clear()
                    listOfTemplates += MultiplicationTemplate(0, 1, 1, 0, 0, 0)
                    listOfTemplates += MultiplicationTemplate(0, 1, 1, 1, 0, 1)
                    listOfTemplates += MultiplicationTemplate(1, 1, 1, 0, 1, 0)
                    listOfTemplates += MultiplicationTemplate(1, 1, 1, 1, 1, 1)
                    listOfTemplates.forEach {
                        launch {
                            firstMatrixPartition[it.firstArrayIndex1][it.firstArrayIndex2].multiplySubMatrix(
                                secondMatrixPartition[it.secondArrayIndex1][it.secondArrayIndex2],
                                temporaryMatrixPartition[it.resultMatrixIndex1][it.resultMatrixIndex2]
                            )
                        }
                    }
                }
            }
        }
    }

    private fun fullSubMatrix(): SubMatrix = SubMatrix(this, 0, numberOfRows - 1, 0, numberOfColumns - 1)

    operator fun times(secondMatrix: Matrix): Matrix {
        require(this.numberOfColumns == secondMatrix.numberOfRows) { "these matrices cannot be multiplied" }
        val resultMatrix = Matrix(this.numberOfRows, secondMatrix.numberOfColumns)
        this.fullSubMatrix().multiplySubMatrix(
            secondMatrix.fullSubMatrix(),
            resultMatrix.fullSubMatrix()
        )
        return resultMatrix
    }

    override fun toString() = array.joinToString("\n") { it.joinToString(" ") }

    override operator fun equals(other: Any?) =
        if (other !is Matrix) false else array.contentDeepEquals(other.array)

    override fun hashCode(): Int {
        var result = numberOfRows
        result = 31 * result + numberOfColumns
        result = 31 * result + array.contentDeepHashCode()
        result = 31 * result + rowRange.hashCode()
        result = 31 * result + columnRange.hashCode()
        return result
    }
}
