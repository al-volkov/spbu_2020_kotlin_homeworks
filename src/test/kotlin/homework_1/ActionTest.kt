package homework_1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IndexOutOfBoundsException

internal class PushForwardTest {
    @Test
    fun executeTest() {
        val storage = PerformedCommandStorage()
        PushForward(1).execute(storage)
        assertEquals(ArrayDeque(listOf(1)), storage.arrayDeque)
        PushForward(2).execute(storage)
        assertEquals(ArrayDeque(listOf(2, 1)), storage.arrayDeque)
    }

    @Test
    fun undoTest() {
        val storage = PerformedCommandStorage()
        PushForward(1).execute(storage)
        PushForward(2).execute(storage)
        PushForward(3).execute(storage)
        storage.undo()
        assertEquals(ArrayDeque(listOf(2, 1)), storage.arrayDeque)
        storage.undo()
        assertEquals(ArrayDeque(listOf(1)), storage.arrayDeque)
        storage.undo()
        assertEquals(ArrayDeque<Int>(), storage.arrayDeque)
    }
}

internal class PushBackTest {
    @Test
    fun executeTest() {
        val storage = PerformedCommandStorage()
        PushBack(1).execute(storage)
        assertEquals(ArrayDeque(listOf(1)), storage.arrayDeque)
        PushBack(2).execute(storage)
        assertEquals(ArrayDeque(listOf(1, 2)), storage.arrayDeque)
    }

    @Test
    fun undoTest() {
        val storage = PerformedCommandStorage()
        PushBack(1).execute(storage)
        PushBack(2).execute(storage)
        PushBack(3).execute(storage)
        storage.undo()
        assertEquals(ArrayDeque(listOf(1, 2)), storage.arrayDeque)
        storage.undo()
        assertEquals(ArrayDeque(listOf(1)), storage.arrayDeque)
        storage.undo()
        assertEquals(ArrayDeque<Int>(), storage.arrayDeque)
    }
}

internal class MoveElementTest {
    @Test
    fun executeTest() {
        val storage = PerformedCommandStorage()
        for (i in 1..4) {
            PushBack(i).execute(storage)
        }
        MoveElement(0, 3).execute(storage)
        assertEquals(ArrayDeque(listOf(2, 3, 4, 1)), storage.arrayDeque)
        MoveElement(1, 2).execute(storage)
        assertEquals(ArrayDeque(listOf(2, 4, 3, 1)), storage.arrayDeque)
    }

    @Test
    fun undoTest() {
        val storage = PerformedCommandStorage()
        for (i in 1..4) {
            PushBack(i).execute(storage)
        }
        MoveElement(0, 3).execute(storage)
        MoveElement(0, 2).execute(storage)
        MoveElement(0, 1).execute(storage)
        storage.undo()
        assertEquals(ArrayDeque(listOf(3, 4, 2, 1)), storage.arrayDeque)
        storage.undo()
        assertEquals(ArrayDeque(listOf(2, 3, 4, 1)), storage.arrayDeque)
        storage.undo()
        assertEquals(ArrayDeque(listOf(1, 2, 3, 4)), storage.arrayDeque)
    }

    @Test
    fun indexOutOfBoundsTest(){
        val storage = PerformedCommandStorage()
        for (i in 1..4) {
            PushBack(i).execute(storage)
        }
        assertThrows<IndexOutOfBoundsException> { MoveElement(0, 4).execute(storage) }
        assertThrows<IndexOutOfBoundsException> { MoveElement(-1, 3).execute(storage) }
        assertThrows<IndexOutOfBoundsException> { MoveElement(-1, 4).execute(storage) }
    }
}
