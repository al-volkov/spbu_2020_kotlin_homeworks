package homework_4

class AVLNode<K : Comparable<K>, V>(private var key: K, private var pvalue: V) {
    val value: V
        get() {
            return pvalue
        }
    private var leftChild: AVLNode<K, V>? = null
    private var rightChild: AVLNode<K, V>? = null
    private var height = 0

    fun balance(): AVLNode<K, V>? {
        leftChild = leftChild?.balance()
        this.updateHeight()
        rightChild = rightChild?.balance()
        this.updateHeight()
        return when {
            this.getBalanceFactor() == 2 -> {
                if (rightChild?.getBalanceFactor() ?: 0 < 0) {
                    rightChild = rightChild?.rotateRight()
                }
                this.rotateLeft()
            }
            this.getBalanceFactor() == -2 -> {
                if (leftChild?.getBalanceFactor() ?: 0 > 0) {
                    leftChild = leftChild?.rotateLeft()
                }
                this.rotateRight()
            }
            else -> {
                this
            }
        }
    }

    private fun getBalanceFactor(): Int {
        return (rightChild?.height ?: 0) - (leftChild?.height ?: 0)
    }

    private fun updateHeight() {
        height = kotlin.math.max((leftChild?.height ?: 0), (rightChild?.height ?: 0)) + 1
    }

    private fun rotateLeft(): AVLNode<K, V>? {
        val p = this.rightChild
        this.rightChild = p?.leftChild
        p?.leftChild = this
        this.updateHeight()
        p?.updateHeight()
        return p
    }

    private fun rotateRight(): AVLNode<K, V>? {
        val p = this.leftChild
        this.leftChild = p?.rightChild
        p?.rightChild = this
        this.updateHeight()
        p?.updateHeight()
        return p
    }

    fun put(key: K, value: V): V? {
        return when {
            key == this.key -> {
                val oldValue = this.pvalue
                this.pvalue = value
                oldValue
            }
            key < this.key -> {
                if (this.leftChild == null) {
                    this.leftChild = AVLNode(key, value)
                    null
                } else {
                    leftChild?.put(key, value)
                }
            }
            key > this.key -> {
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
            key == this.key -> this
            key > this.key -> this.rightChild?.getNode(key)
            key < this.key -> this.leftChild?.getNode(key)
            else -> null
        }
    }

    fun remove(key: K): AVLNode<K, V>? {
        when {
            key < this.key -> {
                leftChild = leftChild?.remove(key)
            }
            key > this.key -> {
                rightChild = rightChild?.remove(key)
            }
            else -> {
                if (leftChild == null) {
                    return rightChild
                } else {
                    if (rightChild == null) {
                        return leftChild
                    } else {
                        val minimalNode: AVLNode<K, V> = rightChild?.min() ?: this
                        this.pvalue = minimalNode.pvalue
                        this.key = minimalNode.key
                        if (rightChild?.key == minimalNode.key) {
                            rightChild = null
                        }
                        rightChild?.removeMin(this.key)
                    }
                }
            }
        }
        return this.balance()
    }

    private fun min(): AVLNode<K, V> {
        return leftChild?.min() ?: this
    }

    private fun removeMin(minKey: K) {
        if (leftChild?.pvalue == minKey) {
            leftChild = null
        } else {
            leftChild?.removeMin(minKey)
        }
    }

    fun containsValue(value: V): Boolean {
        return (this.value == value) || (leftChild?.containsValue(value) ?: false) || (rightChild?.containsValue(value)
            ?: false)
    }

    fun containsKey(key: K): Boolean {
        return this.getNode(key) != null
    }

    fun size(): Int {
        return 1 + (leftChild?.size() ?: 0) + (rightChild?.size() ?: 0)
    }
}
