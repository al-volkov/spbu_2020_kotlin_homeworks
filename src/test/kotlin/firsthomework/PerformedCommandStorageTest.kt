package firsthomework

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PerformedCommandStorageTest {

    @Test
    fun testPerformedCommandStorage() {
        val storage = PerformedCommandStorage()
        PushForward(2).execute(storage)
        PushForward(1).execute(storage)
        PushBack(3).execute(storage)
        PushBack(4).execute(storage)
        assertEquals(ArrayDeque(listOf(1, 2, 3, 4)), storage.arrayDeque)
        repeat(3) {
            storage.undo()
        }
        assertEquals(ArrayDeque(listOf(2)), storage.arrayDeque)
        storage.undo()
        assertEquals(ArrayDeque<Int>(), storage.arrayDeque)
    }

    @Test
    fun serializationTest() {
        val firstStorage = PerformedCommandStorage()
        PushForward(2).execute(firstStorage)
        PushForward(1).execute(firstStorage)
        PushBack(3).execute(firstStorage)
        PushBack(4).execute(firstStorage)
        firstStorage.serialize("src/test/resources/testfile.json")
        val secondStorage = PerformedCommandStorage()
        secondStorage.deserialize("src/test/resources/testfile.json")
        assertEquals(firstStorage.arrayDeque, secondStorage.arrayDeque)
    }
}
