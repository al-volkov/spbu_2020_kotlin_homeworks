package test1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PriorityQueueTest {
    @Test
    fun peekTest() {
        val queue = PriorityQueue<String, Int>()
        queue.enqueue("aa", 1)
        queue.enqueue("cc", 3)
        queue.enqueue("bb", 2)
        assertEquals("cc", queue.peek())
    }

    @Test
    fun removeTest() {
        val queue = PriorityQueue<String, Int>()
        queue.enqueue("aa", 1)
        queue.enqueue("cc", 3)
        queue.enqueue("bb", 2)
        queue.remove()
        assertEquals("bb", queue.peek())
    }

    @Test
    fun roolTest() {
        val queue = PriorityQueue<String, Int>()
        queue.enqueue("aa", 1)
        queue.enqueue("cc", 3)
        queue.enqueue("bb", 2)
        assertEquals("cc", queue.rool())
        assertEquals("bb", queue.peek())
    }

    @Test
    fun emptyQueueTest() {
        val queue = PriorityQueue<Int, Int>()
        assertThrows<NoSuchElementException> { queue.remove() }
        assertThrows<NoSuchElementException> { queue.rool() }
        assertThrows<NoSuchElementException> { queue.peek() }
    }
}
