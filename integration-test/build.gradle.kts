val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val slf4jVersion = rootProject.properties["slf4jVersion"] as String
val komapperVersion = rootProject.properties["komapperVersion"] as String
val jooqVersion = rootProject.properties["jooqVersion"] as String
val exposedVersion = rootProject.properties["exposedVersion"] as String
val ktormVersion = rootProject.properties["ktormVersion"] as String

plugins {
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
    id("org.komapper.gradle") version "3.1.0"
}

dependencies {
    implementation(project(":dsl"))
    implementation(project(":script-serializer"))
    implementation(project(":compiled-serializer"))
    implementation(project(":script-parser"))
    implementation(project(":compiled-parser"))
    implementation(project(":command-client"))
    implementation(project(":client"))
    implementation(project(":custom-komapper-jdbc-change"))
    implementation(project(":custom-jooq-change"))
    implementation(project(":custom-exposed-migration-change"))
    implementation(project(":custom-ktorm-change"))

    // log
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.1")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.5.0")

    // komapper
    implementation("org.komapper:komapper-core:$komapperVersion")
    implementation("org.komapper:komapper-jdbc:$komapperVersion")
    implementation("org.komapper:komapper-annotation:$komapperVersion")
    platform("org.komapper:komapper-platform:$komapperVersion").let {
        implementation(it)
        ksp(it)
    }
    ksp("org.komapper:komapper-processor")
    implementation("org.komapper:komapper-dialect-h2-jdbc:$komapperVersion")

    // jooq
    implementation("org.jooq:jooq:$jooqVersion")

    // exposed
    implementation("org.jetbrains.exposed:exposed-migration:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    // ktorm
    implementation("org.ktorm:ktorm-core:$ktormVersion")

    // test
    testImplementation(kotlin("test"))
    implementation(project(":test-utils"))
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")

    // db-migration
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")

    // h2database
    testImplementation("com.h2database:h2:2.3.232")
}

configurations.all {
    resolutionStrategy {
        force("org.liquibase:liquibase-core:$liquibaseVersion")
    }
}

tasks.test {
    useJUnitPlatform()
    systemProperty("liquibaseVersion", liquibaseVersion)
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.add("-opt-in=org.komapper.annotation.KomapperExperimentalAssociation")
        }
    }
}
