package homework_3

import com.charleskorn.kaml.Yaml
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.Test
import java.io.File

@Serializable
class InformationForTests {
    var packageName: String = ""
    var className: String = ""
    var functionNames: List<String> = emptyList()
}

class OtherClass {
    init {
        print("finish")
    }
}

fun main() {
    val path = "path should be here"
    val information = Yaml.default.decodeFromString(
        InformationForTests.serializer(),
        File(path).inputStream().readAllBytes().toString(Charsets.UTF_8).replace("package name", "packageName")
            .replace("class name", "className").replace("functions", "functionNames").replace("name: ", "")
    )
    println(information.packageName)
    println(information.className)
    for (i in information.functionNames)
        println(i)
    val testFile = FileSpec.builder(information.packageName, "PerformedCommandStorageTest")
    val newClass = TypeSpec.classBuilder(information.className + "Test")
    for (i in information.functionNames) {
        val newFunction = FunSpec.builder(i)
        newFunction.addAnnotation(Test::class)
        newClass.addFunction(newFunction.build())
    }
    testFile.addType(newClass.build())
    testFile.addImport("org.junit.jupiter.api.Assertions", "")
    testFile.build().writeTo(
        File("path should be here")
    )
    OtherClass()
}
