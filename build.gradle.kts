plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    signing
    `maven-publish`
}

group = "io.github.edgeatzero"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    google()
}

dependencies {
    api(kotlin("stdlib"))
    api(kotlin("reflect"))

    // Kodein
    api("org.kodein.di:kodein-di:7.18.0")

    // Compose
    api(compose.runtime)
    api(compose.foundation)
    api("com.alialbaali.kamel:kamel-image:0.4.1")

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    // Network
    api("io.ktor:ktor-client-core:2.3.1")
    api("io.ktor:ktor-utils:2.3.1")

    testApi(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.contracts.ExperimentalContracts")
                optIn("kotlinx.serialization.InternalSerializationApi")
                optIn("kotlinx.serialization.ExperimentalSerializationApi")
            }
        }
    }

}

tasks {
    task<Jar>("SourceJar") {
        archiveClassifier.set("sources")
        from(sourceSets)
    }.dependsOn(classes)
}


signing {
    sign(publishing.publications)
}

publishing {
    repositories {
        maven {
            name = "Sonatype"
            if (version.toString().endsWith("SNAPSHOT")) {
                setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            } else {
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            }
            credentials {
                username = properties["sonatype.username"] as String
                password = properties["sonatype.password"] as String
            }
        }
    }

    publications {
        create<MavenPublication>("Maven") {
            groupId = group.toString()
            artifactId = "Extension-Api"
            version = project.version.toString()
            pom {
                name.set("Extension Api")
                description.set("Extension Api")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://github.com/EdgeAtZero/ExtensionApi/blob/main/LICENSE")
                    }
                }
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/EdgeAtZero/ExtensionApi/issues")
                }
                scm {
                    connection.set("https://github.com/EdgeAtZero/ExtensionApi.git")
                    url.set("https://github.com/EdgeAtZero/ExtensionApi")
                }
                developers {
                    developer {
                        name.set("EdgeAtZero")
                        email.set("edgeatzero@gmail.com")
                    }
                }
            }

            artifact(tasks.jar)
            artifact(tasks.kotlinSourcesJar)
        }
    }
}
