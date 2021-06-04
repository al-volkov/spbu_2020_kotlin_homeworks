package test3new

/**
 * Compresses the byte array by matching each sequence of same elements to the other two:
 * the length of the sequence and the repeating element.
 */
fun ByteArray.compress(): ByteArray {
    if (this.isEmpty()) {
        return this
    }
    val newList = mutableListOf<Byte>()
    var numberOfIdenticalElements = 0
    var lastElement = this[0]
    this.forEach {
        if (it == lastElement) {
            ++numberOfIdenticalElements
        } else {
            if (numberOfIdenticalElements != 0) {
                newList.add(numberOfIdenticalElements.toByte())
                newList.add(lastElement)
            }
            numberOfIdenticalElements = 1
            lastElement = it
        }
    }
    newList.add(numberOfIdenticalElements.toByte())
    newList.add(lastElement)
    return newList.toByteArray()
}

/**
 * Decompresses the byte array, that has been compressed with [compress].
 * Array must contain an even number of elements, even indexes must contain positive values.
 */
fun ByteArray.decompress(): ByteArray {
    require(this.size % 2 == 0) { "this array cannot be decompressed because it contains an odd number of elements" }
    val elementFrequency = this.filterIndexed { index, _ -> index % 2 == 0 }.map { it ->
        val newFrequency = it.toInt()
        require(newFrequency > 0) {
            "this array cannot be decompressed because it contains a non positive value at even index"
        }
        newFrequency
    }
    val elements = this.filterIndexed { index, _ -> index % 2 == 1 }
    val newList = mutableListOf<Byte>()
    for ((frequency, element) in elementFrequency.zip(elements)) {
        repeat(frequency) {
            newList.add(element)
        }
    }
    return newList.toByteArray()
}
