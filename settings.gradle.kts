pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version extra["kotlin.version"] as String
        kotlin("plugin.serialization") version extra["kotlin.version"] as String
        id("org.jetbrains.compose") version extra["compose.version"] as String
    }
}

rootProject.name = "ExtensionApi"
