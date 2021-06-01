package homework_7

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Matrix(private val numberOfRows: Int, private val numberOfColumns: Int) {

    private val array = Array(numberOfRows) { IntArray(numberOfColumns) { 0 } }
    private val rowRange = 0 until numberOfRows
    private val columnRange = 0 until numberOfColumns

    init {
        require(numberOfColumns >= 1 && numberOfRows >= 1) { "size of matrix must be positive integer" }
    }

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
            for ((firstRow, secondRow) in rowRange.zip(secondMatrix.rowRange)) {
                for ((firstColumn, secondColumn) in columnRange.zip(secondMatrix.columnRange)) {
                    this.mainMatrix[firstRow, firstColumn] += secondMatrix.mainMatrix[secondRow, secondColumn]
                }
            }
        }

        private fun oneRowMatrixMultiply(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
            for ((firstColumn, secondRow) in columnRange.zip(
                columnRange.map { it - startColumn + secondMatrix.startRow }))
                for ((secondColumn, resultColumn) in secondMatrix.columnRange.zip(
                    secondMatrix.columnRange.map { it + -secondMatrix.startColumn + resultMatrix.startColumn }))
                    resultMatrix.mainMatrix[resultMatrix.startRow, resultColumn] +=
                        mainMatrix[startRow, firstColumn] *
                                secondMatrix.mainMatrix[secondRow, secondColumn]
        }

        private fun oneColumnMatrixMultiply(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
            for ((firstRow, resultRow) in rowRange.zip(
                rowRange.map { it - startRow + resultMatrix.startRow }))
                for ((secondColumn, resultColumn) in secondMatrix.columnRange.zip(
                    secondMatrix.columnRange.map { it - secondMatrix.startColumn + resultMatrix.startColumn }))
                    resultMatrix.mainMatrix[resultRow, resultColumn] +=
                        this.mainMatrix[firstRow, startColumn] *
                                secondMatrix.mainMatrix[secondMatrix.startRow, secondColumn]
        }

        private fun secondMatrixOneColumnMultiply(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
            for ((firstRow, resultRow) in rowRange.zip(
                rowRange.map { it - startRow + resultMatrix.startRow }))
                for ((firstColumn, secondRow) in columnRange.zip(
                    columnRange.map { it - startColumn + secondMatrix.startRow }))
                    resultMatrix.mainMatrix[resultRow, resultMatrix.startColumn] +=
                        mainMatrix[firstRow, firstColumn] *
                                secondMatrix.mainMatrix[secondRow, secondMatrix.startColumn]
        }

        fun multiplySubMatrix(secondMatrix: SubMatrix, resultMatrix: SubMatrix) {
            when {
                this.numberOfRows == 1 -> this.oneRowMatrixMultiply(secondMatrix, resultMatrix)
                this.numberOfColumns == 1 -> this.oneColumnMatrixMultiply(secondMatrix, resultMatrix)
                secondMatrix.numberOfColumns == 1 -> this.secondMatrixOneColumnMultiply(secondMatrix, resultMatrix)
                else -> {
                    val temporaryMatrix =
                        Matrix(resultMatrix.numberOfRows, resultMatrix.numberOfColumns).fullSubMatrix()
                    val thisMatrixPartition = this.partition()
                    val secondMatrixPartition = secondMatrix.partition()
                    val resultMatrixPartition = resultMatrix.partition()
                    val temporaryMatrixPartition = temporaryMatrix.partition()
                    launchBlockMatrixMultiplication(
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
            val subMatrix11 = SubMatrix(mainMatrix, startRow, midRow, startColumn, midColumn)
            val subMatrix12 = SubMatrix(mainMatrix, startRow, midRow, midColumn + 1, finalColumn)
            val subMatrix21 = SubMatrix(mainMatrix, midRow + 1, finalRow, startColumn, midColumn)
            val subMatrix22 = SubMatrix(mainMatrix, midRow + 1, finalRow, midColumn + 1, finalColumn)
            return arrayOf(arrayOf(subMatrix11, subMatrix12), arrayOf(subMatrix21, subMatrix22))
        }

        companion object {
            const val FIRST_ROW = 0
            const val SECOND_ROW = 1
            const val FIRST_COLUMN = 0
            const val SECOND_COLUMN = 1
            fun launchBlockMatrixMultiplication(
                firstMatrixPartition: Array<Array<SubMatrix>>,
                secondMatrixPartition: Array<Array<SubMatrix>>,
                resultMatrixPartition: Array<Array<SubMatrix>>,
                temporaryMatrixPartition: Array<Array<SubMatrix>>
            ) {
                // Block matrix multiplication is similar to multiplication of square matrices of size 2
                runBlocking {
                    for (row in FIRST_ROW..SECOND_ROW) {
                        for (column in FIRST_COLUMN..SECOND_COLUMN) {
                            launch {
                                firstMatrixPartition[row][FIRST_COLUMN].multiplySubMatrix(
                                    secondMatrixPartition[FIRST_ROW][column],
                                    resultMatrixPartition[row][column]
                                )
                            }
                            launch {
                                firstMatrixPartition[row][SECOND_COLUMN].multiplySubMatrix(
                                    secondMatrixPartition[SECOND_ROW][column],
                                    temporaryMatrixPartition[row][column]
                                )
                            }
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
