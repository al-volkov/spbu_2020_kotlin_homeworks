package test1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TrieTest {
    @Test
    fun emptyTrieTest() {
        val trie = Trie()
        assertEquals(0, trie.size())
    }

    @Test
    fun addAndContainsTest() {
        val trie = Trie()
        trie.add("he")
        trie.add("she")
        trie.add("his")
        trie.add("hers")
        assertTrue(trie.contains("he"))
        assertTrue(trie.contains("she"))
        assertTrue(trie.contains("his"))
        assertTrue(trie.contains("hers"))
        assertFalse(trie.contains("h"))
        assertFalse(trie.contains("sh"))
    }

    @Test
    fun removeTest() {
        val trie = Trie()
        trie.add("he")
        trie.add("she")
        trie.add("his")
        trie.add("hers")
        trie.remove("his")
        assertFalse(trie.contains("his"))
        assertTrue(trie.contains("he")) // to chech that removing doesn't mess up other elements
    }

    @Test
    fun howManyStartWithPrefix() {
        val trie = Trie()
        trie.add("he")
        trie.add("she")
        trie.add("his")
        trie.add("hers")
        assertEquals(2, trie.howManyStartWithPrefix("he"))
        trie.remove("he")
        assertEquals(1, trie.howManyStartWithPrefix("he"))
        assertEquals(1, trie.howManyStartWithPrefix("s"))
        assertEquals(0, trie.howManyStartWithPrefix("e"))
    }

    @Test
    fun sizeTest() {
        val trie = Trie()
        trie.add("he")
        trie.add("she")
        trie.add("his")
        trie.add("hers")
        assertEquals(4, trie.size())
    }
}

