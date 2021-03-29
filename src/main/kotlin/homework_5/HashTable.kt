package homework_5

const val CRITICAL_LOAD_FACTOR = 0.7

interface HashFunction<T> {
    fun getHash(key: T): Int
}

class HashTable<K, V>(private var hashFunction: HashFunction<K>) {
    private data class Element<K, V>(val key: K, val value: V)

    private class Container<K, V> {
        private val listOfElements: MutableList<Element<K, V>> = mutableListOf()

        val size
            get() = listOfElements.size
        val elements
            get() = listOfElements

        fun add(element: Element<K, V>): Boolean {
            return if (this.contains(element.key)) {
                false
            } else {
                listOfElements.add(element)
                true
            }
        }

        fun contains(key: K): Boolean {
            listOfElements.forEach { if (it.key == key) return true }
            return false
        }

        fun remove(key: K): Boolean {
            return if (!this.contains(key)) {
                false
            } else {
                listOfElements.removeAll { it.key == key }
                true
            }
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
        for (container in array) {
            for (element in container.elements) {
                val hash = hashFunction.getHash(element.key) % (size)
                newArray[hash].add(element)
            }
        }
        array = newArray
    }

    fun add(key: K, value: V): Boolean {
        val newElement = Element(key, value)
        val hash = hashFunction.getHash(key) % size
        return if (array[hash].add(newElement)) {
            ++numberOfElements
            if (loadFactor >= CRITICAL_LOAD_FACTOR) {
                this.expand()
            }
            true
        } else {
            false
        }
    }

    fun remove(key: K): Boolean {
        val hash = hashFunction.getHash(key) % size
        return if (array[hash].remove(key)) {
            --numberOfElements
            true
        } else {
            false
        }
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

    class DefaultHashFunction1 : HashFunction<String> {
        override fun getHash(key: String): Int {
            var hash = 0
            for (character in key) {
                hash *= 2
                hash += (character - 'a')
            }
            return hash
        }
    }

    class DefaultHashFunction2 : HashFunction<String> {
        override fun getHash(key: String): Int {
            var hash = 0
            for (character in key.reversed()) {
                hash *= 2
                hash += (character - 'a')
            }
            return hash
        }
    }

    class DefaultHashFunction3 : HashFunction<String> {
        override fun getHash(key: String): Int {
            var hash = 0
            for (character in key.reversed()) {
                hash += (character - 'a')
            }
            return hash
        }
    }
}
