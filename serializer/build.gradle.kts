val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val kotlinVersion = rootProject.properties["kotlinVersion"] as String
val liquibaseKotlinDslVersion = rootProject.properties["liquibaseKotlinDslVersion"] as String
val groupId = rootProject.properties["groupId"] as String

plugins {
    kotlin("jvm")
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    // db-migration
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")

    // test
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")

    // reflection
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
}

tasks.test {
    useJUnitPlatform()
}

afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            create<MavenPublication>("release") {
                from(components["java"])
                groupId = groupId
                artifactId = project.name
                version = liquibaseKotlinDslVersion
            }
        }
    }
}