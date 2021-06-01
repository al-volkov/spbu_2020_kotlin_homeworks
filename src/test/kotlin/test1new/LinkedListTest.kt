package test1new

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class LinkedListTest {
    @Test
    fun addTest() {
        val list = LinkedList<Int>()
        for (i in 0..2) {
            list.add(i)
        }
        for (i in 0..2) {
            assertEquals(i, list.get(i))
        }
    }

    @Test
    fun addWithPositionTest() {
        val list = LinkedList<Int>()
        list.add(2, 0)
        list.add(1, 0)
        list.add(3, 2)
        list.add(4, 3)
        for (i in 0..3) {
            assertEquals(i + 1, list.get(i))
        }
    }

    @Test
    fun getTest() {
        val list = LinkedList<Int>()
        list.add(2, 0)
        list.add(1, 0)
        list.add(3, 2)
        list.add(4, 3)
        assertEquals(1, list.get())
    }

    @Test
    fun getWithPositionTest() {
        val list = LinkedList<Int>()
        for (i in 0..10) {
            list.add(i)
        }
        for (i in 0..10) {
            assertEquals(i, list.get(i))
        }
    }

    @Test
    fun emptyListTest() {
        val list = LinkedList<Int>()
        list.add(0)
        list.remove(0)
        assertTrue(list.isEmpty())
    }

    @Test
    fun removeTest() {
        val list = LinkedList<Int>()
        for (i in 0..4) {
            list.add(i)
        }
        list.remove(0)
        list.remove(1)
        list.remove(2)
        assertEquals(1, list.get())
        assertEquals(3, list.get(1))
    }
}
