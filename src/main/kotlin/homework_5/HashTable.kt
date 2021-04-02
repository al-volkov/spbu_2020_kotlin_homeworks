package homework_5

class HashTable<K, V>(private var hashFunction: HashFunction<K>) {
    companion object {
        const val CRITICAL_LOAD_FACTOR = 0.7
    }

    private data class Element<K, V>(val key: K, var value: V)

    private var size = 1
    private var numberOfElements = 0
    private val loadFactor: Double
        get() = numberOfElements / size.toDouble()
    private var array: Array<MutableList<Element<K, V>>> = Array(size) { mutableListOf() }
    private fun expand() {
        size *= 2
        updateHashTable()
    }

    private fun updateHashTable() {
        val newArray: Array<MutableList<Element<K, V>>> = Array(size) { mutableListOf() }
        array.forEach { list ->
            list.forEach { element ->
                val hash = hashFunction.getHash(element.key) % (size)
                newArray[hash].add(element)
            }
        }
        array = newArray
    }

    fun add(key: K, value: V): V? {
        val oldValue: V?
        val hash = hashFunction.getHash(key) % size
        val list = array[hash]
        val element = list.find { it.key == key }
        if (element != null) {
            oldValue = element.value
            if (value != oldValue) {
                element.value = value
            }
        } else {
            list.add(Element(key, value))
            oldValue = null
            ++numberOfElements
        }
        if (loadFactor >= CRITICAL_LOAD_FACTOR) {
            this.expand()
        }
        return oldValue
    }

    fun remove(key: K): V? {
        val hash = hashFunction.getHash(key) % size
        val list = array[hash]
        val element = list.find { it.key == key }
        if (element != null) {
            list.remove(element)
            --numberOfElements
        }
        return element?.value
    }

    operator fun get(key: K): V? {
        val hash = hashFunction.getHash(key) % size
        val list = array[hash]
        return list.find { it.key == key }?.value
    }

    fun contains(key: K): Boolean {
        val hash = hashFunction.getHash(key) % size
        return array[hash].any { it.key == key }
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
