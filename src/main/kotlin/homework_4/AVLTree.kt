package homework_4

class AVLTree<K : Comparable<K>, V> : Map<K, V> {
    private var root: AVLNode<K, V>? = null
    override val entries: Set<Map.Entry<K, V>>
        get() {
            val entries = mutableSetOf<Map.Entry<K, V>>()
            root?.entries(entries) ?: emptySet<Map.Entry<K, V>>()
            return entries.toSet()
        }
    override val keys: Set<K>
        get() {
            val keys = mutableSetOf<K>()
            root?.keys(keys) ?: emptySet<K>()
            return keys.toSet()
        }
    override var size = 0
    override val values: Collection<V>
        get() {
            val values = mutableSetOf<V>()
            root?.values(values) ?: emptySet<V>()
            return values.toSet()
        }

    override fun containsKey(key: K): Boolean {
        return root?.containsKey(key) ?: false
    }

    override fun containsValue(value: V): Boolean {
        return root?.containsValue(value) ?: false
    }

    override fun get(key: K): V? {
        return root?.getNode(key)?.value
    }

    override fun isEmpty() = root == null

    fun put(key: K, value: V): V? {
        return if (this.isEmpty()) {
            root = AVLNode(key, value)
            ++size
            null
        } else {
            val result = root?.put(key, value)
            if (result == null) {
                ++size
            }
            root = root?.balance()
            result
        }
    }

    fun remove(key: K): V? {
        val previousValue: V? = this.get(key)
        if (previousValue != null) {
            --size
        }
        root = root?.remove(key)
        this.root?.balance()
        return previousValue
    }

    fun clear() {
        root = null
        size = 0
    }
}

class Entry<K, V>(override val key: K, override val value: V) : Map.Entry<K, V>
