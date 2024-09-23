val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val slf4jVersion = rootProject.properties["slf4jVersion"] as String

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    implementation(project(":dsl"))
    implementation(project(":script-serializer"))
    implementation(project(":compiled-serializer"))
    implementation(project(":script-parser"))
    implementation(project(":compiled-parser"))
    implementation(project(":client"))
    implementation(project(":custom-komapper-jdbc-change"))
    testImplementation(project(":test-utils"))

    // db-migration
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")

    // h2database
    implementation("com.h2database:h2:2.3.232")

    // log
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.0")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.5.0")

    // test
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}
tasks.test {
    useJUnitPlatform()
    // https://kotest.io/docs/framework/tags.html#gradle
    systemProperties = System.getProperties().asIterable().associate { it.key.toString() to it.value }
    // https://docs.gradle.org/current/dsl/org.gradle.api.tasks.testing.logging.TestLogging.html
    testLogging {
        // トラブルシューティング時に標準出力に出力したいときは、trueにする
        showStandardStreams = true
        showCauses = true
        showStackTraces = true
        showExceptions = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        events("failed")
    }
    maxHeapSize = "2G"
}

tasks {
    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        // if you want to use multiple liquibase-parser, add bellow code.
        mergeServiceFiles()
        // entry-point
        manifest {
            attributes(
                "Main-Class" to "MainKt"
            )
        }
    }
}
