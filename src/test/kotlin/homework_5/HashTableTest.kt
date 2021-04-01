package homework_5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class HashTableTest {
    @Test
    fun simpleAddAndGetTest() {
        val table = HashTable<String, Int>(DefaultHashFunction1())
        val elements = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4))
        elements.forEach { table.add(it.first, it.second) }
        elements.forEach { assertEquals(table[it.first], it.second) }
    }

    @Test
    fun addTest() {
        val table = HashTable<String, Int>(DefaultHashFunction1())
        val elements = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4))
        elements.forEach { table.add(it.first, it.second) }
        assertEquals(1, table.add("a", 0))
        assertEquals(null, table.add("e", 5))
        val newElements = listOf(Pair("a", 0), Pair("b", 2), Pair("c", 3), Pair("d", 4), Pair("e", 5))
        newElements.forEach { assertEquals(table[it.first], it.second) }
    }

    @Test
    fun containsTest() {
        val table = HashTable<String, Int>(DefaultHashFunction1())
        val elements = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4))
        elements.forEach { table.add(it.first, it.second) }
        elements.forEach { assertTrue(table.contains(it.first)) }
    }

    @Test
    fun removeTest() {
        val table = HashTable<String, Int>(DefaultHashFunction1())
        val elements = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4))
        elements.forEach { table.add(it.first, it.second) }
        assertEquals(1, table.remove("a"))
        assertEquals(3, table.remove("c"))
        assertEquals(null, table.remove("e"))
        assertTrue(table.contains("b"))
        assertTrue(table.contains("d"))
        assertFalse(table.contains("a"))
        assertFalse(table.contains("c"))
    }

    @Test
    fun changeHashFunctionTest() {
        val table = HashTable<String, Int>(DefaultHashFunction1())
        val elements = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("d", 4))
        elements.forEach { table.add(it.first, it.second) }
        table.changeHashFunction(DefaultHashFunction2())
        elements.forEach { assertEquals(table[it.first], it.second) }
        table.changeHashFunction(DefaultHashFunction3())
        elements.forEach { assertEquals(table[it.first], it.second) }
    }

    @Test
    fun largeNumberOfElementsTest() {
        val table = HashTable<String, Int>(DefaultHashFunction1())
        for (i in 0..10000) {
            table.add(i.toString(), i)
        }
        for (i in 0..10000) {
            assertEquals(table[i.toString()], i)
        }
    }
}
