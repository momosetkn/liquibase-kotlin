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
include("script-parser-exterior")
include("compiled-parser")
// serializer
include("serializer-core")
include("script-serializer")
include("compiled-serializer")
// client
include("command-client")
include("client")
// custom-change
include("custom-change-core")
include("custom-komapper-jdbc-change")
include("custom-jooq-change")
include("custom-exposed-migration-change")
include("custom-ktorm-change")
// starter
include("starter-compiled")
include("starter-script")
