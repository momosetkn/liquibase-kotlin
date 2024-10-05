pluginManagement {
    val kotlinVersion: String by settings
    repositories {
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

rootProject.name = "liquibase-kotlin"

// integration-test
include("integration-test")
include("test-utils")
// unit-test
include("liquibase-kotlin.custom-komapper-jdbc-change-unit-test")

// production code
// dsl
include("dsl")
// parser
include("script-parser")
include("compiled-parser")
// serializer
include("serializer-core")
include("script-serializer")
include("compiled-serializer")
// client
include("command-client")
include("client")
include("custom-komapper-jdbc-change")
