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
include("wrapper")
include("custom-komapper-jdbc-change")
