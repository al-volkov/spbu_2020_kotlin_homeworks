package homework_5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class HashTableTest {
    @Test
    fun addAndGetTest() {
        val table = HashTable<String, Int>(HashTable.DefaultHashFunction1())
        val elements = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4))
        elements.forEach { table.add(it.first, it.second) }
        elements.forEach { assertEquals(table[it.first], it.second) }
    }

    @Test
    fun containsTest() {
        val table = HashTable<String, Int>(HashTable.DefaultHashFunction1())
        val elements = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4))
        elements.forEach { table.add(it.first, it.second) }
        elements.forEach { assertTrue(table.contains(it.first)) }
    }

    @Test
    fun removeTest() {
        val table = HashTable<String, Int>(HashTable.DefaultHashFunction1())
        val elements = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4))
        elements.forEach { table.add(it.first, it.second) }
        table.remove("a")
        table.remove("c")
        assertTrue(table.contains("b"))
        assertTrue(table.contains("d"))
        assertFalse(table.contains("a"))
        assertFalse(table.contains("c"))
    }
}
