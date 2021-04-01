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
        if (list.any { it.key == key }) {
            val element = list.first { it.key == key }
            oldValue = element.value
            if (oldValue != value) {
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
        if (list.any { it.key == key }) {
            val element = list.first { it.key == key }
            val oldValue = element.value
            list.remove(element)
            --numberOfElements
            return oldValue
        }
        return null
    }

    operator fun get(key: K): V? {
        val hash = hashFunction.getHash(key) % size
        val list = array[hash]
        if (list.any { it.key == key }) {
            return list.first { it.key == key }.value
        }
        return null
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
