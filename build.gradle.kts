val kotestVersion: String by project
val kotlinVersion: String by project
val liquibaseVersion: String by project

plugins {
    kotlin("jvm") version "2.0.10"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

group = "momosetkn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // modules
    implementation(project(":dsl"))
    implementation(project(":parser"))
    implementation(project(":serializer"))

    // test
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")

    // db-migration
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("info.picocli:picocli:4.7.6")
    implementation(kotlin("stdlib-jdk8"))
}
kotlin {
    jvmToolchain(17)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        freeCompilerArgs.addAll(listOf("-Xcontext-receivers", "-Xjvm-default=all"))
    }
}

ktlint {
    version.set("1.3.1")
}
