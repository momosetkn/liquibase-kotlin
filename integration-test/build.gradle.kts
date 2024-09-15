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
    implementation(project(":script-serializer"))
    implementation(project(":typesafe-serializer"))
    implementation(project(":script-parser"))
    implementation(project(":typesafe-parser"))
    implementation(project(":client"))

    // log
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.23.1")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.5.0")

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
    testImplementation("org.postgresql:postgresql:42.7.4")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")
}
tasks.test {
    useJUnitPlatform()
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
}
