package homework_4

class AVLTree<K : Comparable<K>, V> : Map<K, V> {
    private var root: AVLNode<K, V>? = null
    private var psize = 0
    override val entries: Set<Map.Entry<K, V>>
        get() {
            val entries = mutableSetOf<AVLNode<K, V>>()
            root?.entries(entries) ?: emptySet<AVLNode<K, V>>()
            return entries.toSet()
        }
    override val keys: Set<K>
        get() {
            return entries.map { it.key }.toSet()
        }
    override val size: Int
        get() = psize
    override val values: Collection<V>
        get() {
            return entries.map { it.value }.toSet()
        }

    override fun containsKey(key: K) = get(key) != null
    override fun containsValue(value: V) = root?.containsValue(value) ?: false

    override fun get(key: K) = root?.getNode(key)?.value

    override fun isEmpty() = root == null

    fun put(key: K, value: V): V? {
        return if (this.isEmpty()) {
            root = AVLNode(key, value)
            ++psize
            null
        } else {
            val result = root?.put(key, value)
            if (result == null) {
                ++psize
            }
            root = root?.balance()
            result
        }
    }

    fun remove(key: K): V? {
        val previousValue: V? = this[key]
        if (previousValue != null) {
            --psize
        }
        root = root?.remove(key)
        this.root?.balance()
        return previousValue
    }
}
