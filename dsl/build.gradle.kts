val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val liquibaseKotlinDslVersion = rootProject.properties["liquibaseKotlinDslVersion"] as String

plugins {
    kotlin("jvm")
}

group = "com.github.momosetkn"
version = liquibaseKotlinDslVersion

repositories {
    mavenCentral()
}

dependencies {
    // db-migration
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")

    // test
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
}
