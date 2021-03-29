package homework_3

import com.charleskorn.kaml.Yaml
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File

/**
 * Class for correct parsing
 */
@Serializable
class FunctionName(
    val name: String = ""
)

/**
 * Class for deserializing the config
 * @property packageName name of the package in which the generated file will be located
 * @property className name of the class that will contain the testing functions
 * @property functionNames list with names of the functions to be generated
 */
@Serializable
class InformationForTests(
    @SerialName("package name")
    val packageName: String,

    @SerialName("class name")
    val className: String,

    @SerialName("functions")
    val functionNames: List<FunctionName>
)

class TestGenerator(pathToConfig: String) {
    private var file: FileSpec

    init {
        val config = File(pathToConfig).readText()
        val information = Yaml.default.decodeFromString(InformationForTests.serializer(), config)
        val newFile = FileSpec.builder(information.packageName, "${information.className}Test")
        newFile.addType(generateClassForTests("${information.className}Test", information.functionNames))
        file = newFile.build()
    }

    /**
     * Generates a function by name
     */
    private fun generateFunctionForTests(functionName: String) =
        FunSpec.builder(functionName).addAnnotation(ClassName("org.junit.jupiter.api", "Test")).build()

    /**
     * Generate class by name and containing functions
     */
    private fun generateClassForTests(className: String, functionNames: List<FunctionName>): TypeSpec {
        val newClass = TypeSpec.classBuilder(className).addModifiers(KModifier.INTERNAL)
        for (functionName in functionNames) {
            newClass.addFunction(generateFunctionForTests(functionName.name))
        }
        return newClass.build()
    }

    fun writeTo(path: String) {
        file.writeTo(File(path))
    }

    fun getString(): String = file.toString()
}

fun main() {
    TestGenerator("src/main/resources/testconfig.yaml").writeTo("src/main/resources/kotlin/homework_3")
}
