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

// test
include("integration-test")
include("fat-jar-integration-test")
include("test-utils")

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
include("client")
include("custom-komapper-jdbc-change")
