package homework_4

class AVLNode<K : Comparable<K>, V>(private val pkey: K, private var pvalue: V) : Map.Entry<K, V> {
    private var leftChild: AVLNode<K, V>? = null
    private var rightChild: AVLNode<K, V>? = null
    private val height: Int
        get() = kotlin.math.max((leftChild?.height ?: 0), (rightChild?.height ?: 0)) + 1
    private val balanceFactor: Int
        get() = (rightChild?.height ?: 0) - (leftChild?.height ?: 0)
    override val key: K
        get() = pkey
    override val value: V
        get() = pvalue

    fun balance(): AVLNode<K, V>? {
        leftChild = leftChild?.balance()
        rightChild = rightChild?.balance()
        return when {
            this.balanceFactor > 1 -> {
                if (rightChild?.balanceFactor ?: 0 < 0) {
                    rightChild = rightChild?.rotateRight()
                }
                this.rotateLeft()
            }
            this.balanceFactor < -1 -> {
                if (leftChild?.balanceFactor ?: 0 > 0) {
                    leftChild = leftChild?.rotateLeft()
                }
                this.rotateRight()
            }
            else -> this
        }
    }

    private fun rotateLeft(): AVLNode<K, V>? {
        val p = this.rightChild
        this.rightChild = p?.leftChild
        p?.leftChild = this
        return p
    }

    private fun rotateRight(): AVLNode<K, V>? {
        val p = this.leftChild
        this.leftChild = p?.rightChild
        p?.rightChild = this
        return p
    }

    fun put(key: K, value: V): V? {
        return when {
            key == this.pkey -> {
                val oldValue = this.pvalue
                this.pvalue = value
                oldValue
            }
            key < this.pkey -> {
                if (this.leftChild == null) {
                    this.leftChild = AVLNode(key, value)
                    null
                } else {
                    leftChild?.put(key, value)
                }
            }
            key > this.pkey -> {
                if (this.rightChild == null) {
                    this.rightChild = AVLNode(key, value)
                    null
                } else {
                    rightChild?.put(key, value)
                }
            }
            else -> null
        }
    }

    fun getNode(key: K): AVLNode<K, V>? {
        return when {
            key == this.pkey -> this
            key > this.pkey -> this.rightChild?.getNode(key)
            key < this.pkey -> this.leftChild?.getNode(key)
            else -> null
        }
    }

    fun remove(key: K): AVLNode<K, V>? {
        return when {
            key < this.pkey -> {
                leftChild = leftChild?.remove(key)
                this
            }
            key > this.pkey -> {
                rightChild = rightChild?.remove(key)
                this
            }
            else -> {
                when {
                    leftChild == null -> {
                        rightChild
                    }
                    rightChild == null -> {
                        leftChild
                    }
                    else -> {
                        val minimalNode: AVLNode<K, V> = rightChild?.getMin() ?: this
                        minimalNode.leftChild = this.leftChild
                        if (minimalNode.pkey != rightChild?.pkey) {
                            rightChild?.removeMin(minimalNode.pkey, minimalNode.rightChild)
                            minimalNode.rightChild = this.rightChild
                        }
                        minimalNode.balance()
                    }
                }
            }
        }
    }

    private fun getMin(): AVLNode<K, V> = leftChild?.getMin() ?: this

    private fun removeMin(minKey: K, newLeftChild: AVLNode<K, V>?) {
        if (leftChild?.pkey == minKey) {
            leftChild = newLeftChild
        } else {
            leftChild?.removeMin(minKey, newLeftChild)
        }
    }

    fun containsValue(value: V): Boolean =
        this.value == value ||
        leftChild?.containsValue(value) ?: false ||
        rightChild?.containsValue(value) ?: false

    fun entries(entries: MutableSet<AVLNode<K, V>>) {
        entries.add(this)
        leftChild?.entries(entries)
        rightChild?.entries(entries)
    }
}
