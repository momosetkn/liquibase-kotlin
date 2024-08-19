import java.net.URI

val kotestVersion: String by project
val kotlinVersion: String by project
val liquibaseVersion: String by project
val liquibaseKotlinDslVersion: String by project

plugins {
    kotlin("jvm") version "2.0.10"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    `maven-publish`
}

group = "momosetkn"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
        maven { url = URI("https://jitpack.io") }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    val sourcesJar by tasks.creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["java"])
                artifact(sourcesJar)
                groupId = "com.github.takagimeow"
                artifactId = project.name
                version = liquibaseKotlinDslVersion
            }
        }
        repositories {
            maven { url = URI("https://jitpack.io") }
        }
    }
}

dependencies {
    // modules
    api(project(":dsl"))
    api(project(":parser"))
    api(project(":serializer"))

    // test
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")

    // db-migration
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("info.picocli:picocli:4.7.6")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        freeCompilerArgs.addAll(listOf("-Xcontext-receivers", "-Xjvm-default=all"))
    }
}

ktlint {
    version.set("1.3.1")
}
