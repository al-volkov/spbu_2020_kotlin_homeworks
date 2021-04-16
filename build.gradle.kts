import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm") version "1.4.32"
    id("io.gitlab.arturbosch.detekt") version "1.15.0"
    kotlin("plugin.serialization") version "1.4.32"
    id("org.jetbrains.dokka") version "1.4.30"
    id("org.openjfx.javafxplugin") version "0.0.8"
    application
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-reflect:1.4.32")
    testImplementation(platform("org.junit:junit-bom:5.7.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    implementation("com.squareup:kotlinpoet:1.7.2")
    implementation("com.charleskorn.kaml:kaml:0.28.3")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.14.2")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.openjfx:javafx-base:11.0.2")
    implementation("org.openjfx:javafx:11.0.2")
    implementation("org.openjfx:javafx-controls:11.0.2")
}

javafx {
    modules("javafx.controls")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(
            TestLogEvent.STANDARD_ERROR,
            TestLogEvent.STARTED,
            TestLogEvent.PASSED,
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED
        )
    }
}

detekt {
    failFast = true // fail build on any finding
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(
            TestLogEvent.STANDARD_ERROR,
            TestLogEvent.STARTED,
            TestLogEvent.PASSED,
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED
        )
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Werror")
    }
}

tasks.dokkaHtml {
    dokkaSourceSets {
        configureEach {
            includeNonPublic.set(true)
            platform.set(org.jetbrains.dokka.Platform.jvm)
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(
                    URL(
                        "https://github.com/volkov-alexander-d/spbu_2020_kotlin_homeworks/tree/master/src/main/kotlin"
                    )
                )
                remoteLineSuffix.set("#L")
            }
        }
    }
}

application {
    mainClass.set("MainKt")
}
