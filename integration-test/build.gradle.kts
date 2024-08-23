val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val testcontainersVersion = rootProject.properties["testcontainersVersion"] as String

plugins {
    kotlin("jvm")
}

group = "momosetkn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":dsl"))
    implementation(project(":serializer"))
    implementation(project(":parser"))

    // test
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")

    // db-migration
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("info.picocli:picocli:4.7.6")

    // testcontainers
    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    // postgresql
    testImplementation("org.postgresql:postgresql:42.7.3")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
}
