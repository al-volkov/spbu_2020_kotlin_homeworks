package homework_4

class AVLTree<K : Comparable<K>, V> {
    private var root: AVLNode<K, V>? = null

    fun size(): Int {
        return root?.size() ?: 0
    }

    fun isEmpty() = root == null

    fun containsKey(key: K): Boolean {
        return root?.containsKey(key) ?: false
    }

    fun containsValue(value: V): Boolean {
        return root?.containsValue(value) ?: false
    }

    fun get(key: K): V? {
        return root?.getNode(key)?.value
    }

    fun put(key: K, value: V): V? {
        return if (this.isEmpty()) {
            root = AVLNode(key, value)
            null
        } else {
            val result = root?.put(key, value)
            root = root?.balance()
            result
        }
    }

    fun remove(key: K): V? {
        val previousValue: V? = this.get(key)
        root = root?.remove(key)
        return previousValue
    }

    fun clear() {
        root = null
    }
}
