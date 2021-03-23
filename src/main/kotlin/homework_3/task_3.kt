package homework_3

import com.charleskorn.kaml.Yaml
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.Test
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
    var packageName: String,

    @SerialName("class name")
    var className: String,

    @SerialName("functions")
    var functionNames: List<FunctionName>
)

/**
 * Generates a function by name
 */
fun generateFunctionForTests(functionName: String) = FunSpec.builder(functionName).addAnnotation(Test::class).build()

/**
 * Generate class by name and containing functions
 */
fun generateClassForTests(className: String, functionNames: List<FunctionName>): TypeSpec {
    val newClass = TypeSpec.classBuilder(className).addModifiers(KModifier.INTERNAL)
    for (i in functionNames) {
        newClass.addFunction(generateFunctionForTests(i.name))
    }
    return newClass.build()
}

/**
 * Generates a file for tests
 * @param pathToConfig  path to the yaml file containing information about the file to be generated
 */
fun generateFileForTests(pathToConfig: String): FileSpec {
    val config = File(pathToConfig).readText()
    val information = Yaml.default.decodeFromString(InformationForTests.serializer(), config)
    information.className = "${information.className}Test"
    val newFile = FileSpec.builder(information.packageName, information.className)
    newFile.addType(generateClassForTests(information.className, information.functionNames))
    return newFile.build()
}

/**
 * Generates a file for tests
 * @param pathToConfig  path to the yaml file containing information about the file to be generated
 * @param output path to the directory where the generated file will be placed
 */
fun generateFileForTests(pathToConfig: String, output: String) {
    generateFileForTests(pathToConfig).writeTo(File(output))
}

fun main() {
    generateFileForTests("src/main/resources/testconfig.yaml", "src/main/resources")
}
