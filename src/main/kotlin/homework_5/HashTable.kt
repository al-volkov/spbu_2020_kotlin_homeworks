package homework_5

class HashTable<K, V>(private var hashFunction: HashFunction<K>) {
    companion object {
        const val CRITICAL_LOAD_FACTOR = 0.7
    }

    private data class Element<K, V>(val key: K, val value: V)

    private class Container<K, V> {
        private val listOfElements: MutableList<Element<K, V>> = mutableListOf()

        val size
            get() = listOfElements.size
        val elements
            get() = listOfElements

        fun add(element: Element<K, V>): Boolean {
            if (this.contains(element.key)) {
                return false
            }
            listOfElements.add(element)
            return true
        }

        fun contains(key: K): Boolean {
            listOfElements.forEach { if (it.key == key) return true }
            return false
        }

        fun remove(key: K): Boolean {
            if (!this.contains(key)) {
                return false
            }
            listOfElements.removeAll { it.key == key }
            return true
        }

        fun get(key: K): V? {
            listOfElements.forEach { if (it.key == key) return it.value }
            return null
        }
    }

    private var size = 1
    private var numberOfElements = 0
    private val loadFactor: Double
        get() = numberOfElements / size.toDouble()
    private var array: Array<Container<K, V>> = Array(size) { Container() }
    private fun expand() {
        size *= 2
        updateHashTable()
    }

    private fun updateHashTable() {
        val newArray: Array<Container<K, V>> = Array(size) { Container() }
        array.forEach { container ->
            container.elements.forEach { element ->
                val hash = hashFunction.getHash(element.key) % (size)
                newArray[hash].add(element)
            }
        }
        array = newArray
    }

    fun add(key: K, value: V): Boolean {
        val newElement = Element(key, value)
        val hash = hashFunction.getHash(key) % size
        if (!array[hash].add(newElement)) {
            return false
        }
        ++numberOfElements
        if (loadFactor >= Companion.CRITICAL_LOAD_FACTOR) {
            this.expand()
        }
        return true

    }

    fun remove(key: K): Boolean {
        val hash = hashFunction.getHash(key) % size
        if (!array[hash].remove(key)) {
            return false
        }
        --numberOfElements
        return true
    }

    operator fun get(key: K): V? {
        val hash = hashFunction.getHash(key) % size
        return array[hash].get(key)
    }

    fun contains(key: K): Boolean {
        val hash = hashFunction.getHash(key) % size
        return array[hash].contains(key)
    }

    fun getStatistics(): String {
        var statistics = "number of elements: $numberOfElements\nsize: $size\nload factor: $loadFactor\n"
        var numberOfConflicts = 0
        var maxLengthOfList = 0
        array.forEach {
            if (it.size > 1) {
                ++numberOfConflicts
                maxLengthOfList = kotlin.math.max(maxLengthOfList, it.size)
            }
        }
        statistics += "number of conflicts: $numberOfConflicts\nmax length of list: $maxLengthOfList\n"
        return statistics
    }

    fun changeHashFunction(newHashFunction: HashFunction<K>) {
        hashFunction = newHashFunction
        updateHashTable()
    }
}
