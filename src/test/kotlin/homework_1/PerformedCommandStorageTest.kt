package homework_1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

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
    fun serializationTest(@TempDir tempDir: Path) {
        val pathToTemporaryFile = tempDir.resolve("testfile.json")
        val firstStorage = PerformedCommandStorage()
        PushForward(2).execute(firstStorage)
        PushForward(1).execute(firstStorage)
        PushBack(3).execute(firstStorage)
        PushBack(4).execute(firstStorage)
        firstStorage.serialize(pathToTemporaryFile.toString())
        val secondStorage = PerformedCommandStorage()
        secondStorage.deserialize(pathToTemporaryFile.toString())
        assertEquals(firstStorage.arrayDeque, secondStorage.arrayDeque)
    }
}
