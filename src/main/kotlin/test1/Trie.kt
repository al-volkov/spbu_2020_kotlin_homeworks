package test1

class Trie {
    private val root = Vertex()
    private var size = 0

    private class Vertex {
        val edgesFromThisVertex: MutableMap<Char, Vertex> = mutableMapOf()
        var isFinal: Boolean = false
        var numberOfPrefixes = 0
    }

    private fun isEmpty() = size == 0

    fun add(element: String): Boolean {
        return if (this.contains(element)) {
            false
        } else {
            var currentVertex = this.root
            for (character in element) {
                currentVertex.numberOfPrefixes++
                if (currentVertex.edgesFromThisVertex[character] != null) {
                    currentVertex = currentVertex.edgesFromThisVertex[character]!!
                } else {
                    val newChild = Vertex()
                    currentVertex.edgesFromThisVertex[character] = newChild
                    currentVertex = newChild
                }
            }
            ++size
            currentVertex.numberOfPrefixes++
            currentVertex.isFinal = true
            true
        }
    }

    fun contains(element: String): Boolean {
        return if (this.isEmpty()) {
            false
        } else {
            var currentVertex = this.root
            for (character in element) {
                currentVertex = currentVertex.edgesFromThisVertex[character] ?: return false
            }
            currentVertex.isFinal
        }
    }

    fun remove(element: String): Boolean {
        return if (!this.contains(element)) {
            false
        } else {
            var currentVertex = this.root
            for (character in element) {
                currentVertex.numberOfPrefixes--
                currentVertex = currentVertex.edgesFromThisVertex[character] ?: return false
            }
            currentVertex.numberOfPrefixes--
            currentVertex.isFinal = false
            --size
            true
        }
    }

    fun size() = size

    fun howManyStartWithPrefix(prefix: String): Int {
        if (this.isEmpty()) {
            return 0
        } else {
            var currentVertex = this.root
            for (character in prefix) {
                currentVertex = currentVertex.edgesFromThisVertex[character] ?: return 0
            }
            return currentVertex.numberOfPrefixes
        }
    }
}
