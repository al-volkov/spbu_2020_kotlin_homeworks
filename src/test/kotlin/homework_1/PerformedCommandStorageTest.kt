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
        MoveElement(0, 2).execute(storage)
        MoveElement(1, 0).execute(storage)
        PushForward(0).execute(storage)
        PushBack(5).execute(storage)
        repeat(3) {
            storage.undo()
        }
        assertEquals(ArrayDeque(listOf(2, 3, 1, 4)), storage.arrayDeque)
    }

    @Test
    fun serializeTest(@TempDir tempDir: Path) {
        val storage = PerformedCommandStorage()
        val path = tempDir.resolve("testFile.json").toString()
        PushForward(2).execute(storage)
        PushForward(1).execute(storage)
        PushBack(3).execute(storage)
        PushBack(4).execute(storage)
        MoveElement(0, 3).execute(storage)
        MoveElement(2, 0).execute(storage)
        MoveElement(1, 2).execute(storage)
        storage.serialize(path)
        assertEquals(
            File(tempDir.resolve("testFile.json").toString()).readText(),
            PerformedCommandStorageTest::class.java.getResource("expectedActions.json").readText()
        )
    }

    @Test
    fun deserializeTest(){
        val storage = PerformedCommandStorage()
        storage.deserialize(PerformedCommandStorageTest::class.java.getResource("testActions.json").path)
        assertEquals(ArrayDeque(listOf(3, 5, 1)), storage.arrayDeque)
    }
}
