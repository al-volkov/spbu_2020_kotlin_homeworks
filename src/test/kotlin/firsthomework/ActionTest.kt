package firsthomework

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ActionTest {

    @Test
    fun testPushForward() {
        val storage = PerformedCommandStorage()
        PushForward(1).execute(storage)
        assertEquals(ArrayDeque(listOf(1)), storage.arrayDeque)
        PushForward(2).execute(storage)
        assertEquals(ArrayDeque(listOf(2, 1)), storage.arrayDeque)
    }

    @Test
    fun testPushBack() {
        val storage = PerformedCommandStorage()
        PushBack(3).execute(storage)
        assertEquals(ArrayDeque(listOf(3)), storage.arrayDeque)
        PushBack(4).execute(storage)
        assertEquals(ArrayDeque(listOf(3, 4)), storage.arrayDeque)
    }

    @Test
    fun testMoveElement() {
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
    fun testUndo() {
        val storage = PerformedCommandStorage()
        PushForward(2).execute(storage)
        PushForward(1).execute(storage)
        PushBack(3).execute(storage)
        PushBack(4).execute(storage)
        MoveElement(0, 3).execute(storage)
        storage.undo()
        assertEquals(ArrayDeque(listOf(1, 2, 3, 4)), storage.arrayDeque)
        storage.undo()
        assertEquals(ArrayDeque(listOf(1, 2, 3)), storage.arrayDeque)
        storage.undo()
        storage.undo()
        assertEquals(ArrayDeque(listOf(2)), storage.arrayDeque)
    }
}
