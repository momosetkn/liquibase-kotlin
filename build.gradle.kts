val kotestVersion: String by project
val kotlinVersion: String by project
val liquibaseVersion: String by project
val artifactIdPrefix: String by project
val liquibaseKotlinVersion: String by project

plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    `maven-publish`
}

group = "momosetkn"
version = "1.0-SNAPSHOT"

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
            freeCompilerArgs.addAll(listOf("-Xjvm-default=all"))
        }
    }

    detekt {
        parallel = true
        autoCorrect = true
        config.from("$rootDir/config/detekt/detekt.yml")
        buildUponDefaultConfig = true
        basePath = rootDir.absolutePath
    }

    ktlint {
        version.set("1.3.1")
        filter {
            exclude { entry ->
                entry.file.toString().contains("/generated/")
            }
        }
    }
}

val libraryProjects =
    subprojects.filter {
        it.name in listOf(
            // dsl
            "dsl",
            // parser
            "script-parser",
            "compiled-parser",
            // serializer
            "serializer-core",
            "script-serializer",
            "compiled-serializer",
            // client
            "client",
            "wrapper",
            // custom-change
            "custom-komapper-jdbc-change",
        )
    }
configure(libraryProjects) {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    val sourcesJar by tasks.creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["java"])
                artifact(sourcesJar)
                groupId = "com.github.momosetkn"
                artifactId = "$artifactIdPrefix-${project.name}"
                version = liquibaseKotlinVersion
            }
        }
        repositories {
            maven { url = uri("https://jitpack.io") }
        }
    }
}

dependencies {}
