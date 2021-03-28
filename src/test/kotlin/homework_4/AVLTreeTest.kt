package homework_4

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class AVLTreeTest {

    @Test
    fun putAndEntriesTest() {
        val tree = AVLTree<Int, String>()
        val elements =
            setOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c"), Pair(4, "d"), Pair(5, "e"), Pair(6, "f"), Pair(7, "g"))
        elements.forEach { tree.put(it.first, it.second) }
        assertEquals(elements, tree.entries.map { Pair(it.key, it.value) }.toSet())
    }

    @Test
    fun emptyTreeTest() {
        val tree = AVLTree<Int, String>()
        assertEquals(0, tree.size)
        assertTrue(tree.isEmpty())
    }

    @Test
    fun sizeTest() {
        val tree = AVLTree<Int, String>()
        val elements =
            setOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c"), Pair(4, "d"), Pair(5, "e"), Pair(6, "f"), Pair(7, "g"))
        elements.forEach { tree.put(it.first, it.second) }
        assertEquals(7, tree.size)
    }

    @Test
    fun isEmptyTest() {
        val tree = AVLTree<Int, String>()
        val elements =
            setOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c"), Pair(4, "d"), Pair(5, "e"), Pair(6, "f"), Pair(7, "g"))
        elements.forEach { tree.put(it.first, it.second) }
        assertFalse(tree.isEmpty())
    }

    @Test
    fun containsKeyTest() {
        val tree = AVLTree<Int, String>()
        val elements =
            setOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c"), Pair(4, "d"), Pair(5, "e"), Pair(6, "f"), Pair(7, "g"))
        elements.forEach { tree.put(it.first, it.second) }
        elements.forEach { assertTrue(tree.containsKey(it.first)) }
    }

    @Test
    fun containsValueTest() {
        val tree = AVLTree<Int, String>()
        val elements =
            setOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c"), Pair(4, "d"), Pair(5, "e"), Pair(6, "f"), Pair(7, "g"))
        elements.forEach { tree.put(it.first, it.second) }
        elements.forEach { assertTrue(tree.containsValue(it.second)) }
    }

    @Test
    fun getTest() {
        val tree = AVLTree<Int, String>()
        val elements =
            setOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c"), Pair(4, "d"), Pair(5, "e"), Pair(6, "f"), Pair(7, "g"))
        elements.forEach { tree.put(it.first, it.second) }
        elements.forEach { assertEquals(it.second, tree[it.first]) }
    }

    @Test
    fun removeTest() {
        val tree = AVLTree<Int, String>()
        val elements =
            setOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c"), Pair(4, "d"), Pair(5, "e"), Pair(6, "f"), Pair(7, "g"))
        elements.forEach { tree.put(it.first, it.second) }
        for (i in 1..7 step 2) {
            tree.remove(i)
        }
        assertEquals(elements.filter { it.first % 2 == 0 }.toSet(), tree.entries.map { Pair(it.key, it.value) }.toSet())
    }

    @Test
    fun keysTest() {
        val tree = AVLTree<Int, String>()
        val elements =
            setOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c"), Pair(4, "d"), Pair(5, "e"), Pair(6, "f"), Pair(7, "g"))
        elements.forEach { tree.put(it.first, it.second) }
        assertEquals(elements.map { it.first }.toSet(), tree.entries.map { it.key }.toSet())
    }

    @Test
    fun valuesTest() {
        val tree = AVLTree<Int, String>()
        val elements =
            setOf(Pair(1, "a"), Pair(2, "b"), Pair(3, "c"), Pair(4, "d"), Pair(5, "e"), Pair(6, "f"), Pair(7, "g"))
        elements.forEach { tree.put(it.first, it.second) }
        assertEquals(elements.map { it.second }.toSet(), tree.entries.map { it.value }.toSet())
    }
}
