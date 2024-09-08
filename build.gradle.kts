import java.net.URI

val kotestVersion: String by project
val kotlinVersion: String by project
val liquibaseVersion: String by project
val artifactIdPrefix: String by project
val liquibaseKotlinDslVersion: String by project

plugins {
    kotlin("jvm") version "2.0.10"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    `maven-publish`
}

group = "momosetkn"
version = "1.0-SNAPSHOT"

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
            freeCompilerArgs.addAll(listOf("-Xcontext-receivers", "-Xjvm-default=all"))
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
    }
}

val libraryProjects =
    subprojects.filter {
        it.name in listOf("dsl", "parser", "typesafe-parser", "serializer")
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
                version = liquibaseKotlinDslVersion
            }
        }
        repositories {
            maven { url = URI("https://jitpack.io") }
        }
    }
}

dependencies {}
