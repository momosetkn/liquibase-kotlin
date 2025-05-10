val kotestVersion: String by project
val kotlinVersion: String by project
val liquibaseVersion: String by project
val artifactIdPrefix: String by project
val artifactVersion: String by project
val artifactGroup: String by project

val jvmTargetVersion = 17
val javaLanguageVersion = 21

plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("org.jlleitschuh.gradle.ktlint") version "12.2.0"
    `maven-publish`
    id("io.deepmedia.tools.deployer") version "0.18.0"
    id("org.jetbrains.dokka") version "2.0.0"
}

group = artifactGroup
version = artifactVersion
description = "Liquibase kotlin(DSL, Wrapper client, ORM integration)"

fun validateArtifactVersion(artifactVersion: String) {
    val (artifactVersionLiquibase, artifactVersionLiquibaseKotlin) = artifactVersion.split("-")
    require(artifactVersionLiquibase == liquibaseVersion) {
        "artifactVersion is not matched `liquibaseVersion`"
    }
    require(artifactVersionLiquibaseKotlin.isNotEmpty()) {
        "specify the version after the `-` character of `artifactVersion`"
    }
}
if (artifactVersion.isNotEmpty()) {
    validateArtifactVersion(artifactVersion)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaLanguageVersion))
    }
    withSourcesJar()
    withJavadocJar()
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
        // for snapshot version
        maven {
            url = uri("https://maven.pkg.github.com/liquibase/liquibase")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }

    tasks {
        withType<JavaCompile>().configureEach {
            options.release.set(jvmTargetVersion)
        }

        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
            compilerOptions {
                freeCompilerArgs.addAll(
                    listOf(
                        "-Xjvm-default=all",
                        "-Xjdk-release=$jvmTargetVersion"
                    )
                )
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(jvmTargetVersion.toString()))
            }
        }
    }

    detekt {
        parallel = true
        autoCorrect = true
        config.from("$rootDir/config/detekt/detekt.yml")
        buildUponDefaultConfig = true
        basePath = rootDir.absolutePath
    }

    ktlint {
        version.set("1.3.1")
        filter {
            exclude { entry ->
                entry.file.toString().contains("/generated/")
            }
        }
    }
}

val libraryProjects =
    subprojects.filter {
        // To prevent accidental publishing, use a allowlist.
        it.name in listOf(
            // dsl
            "dsl",
            // parser
            "script-parser",
            "script-parser-exterior",
            "compiled-parser",
            // serializer
            "serializer-core",
            "script-serializer",
            "compiled-serializer",
            // client
            "command-client",
            "client",
            // custom-change
            "custom-komapper-jdbc-change",
            "custom-jooq-change",
            "custom-change-core",
            "custom-exposed-migration-change",
            "custom-ktorm-change",
            // starter
            "starter-compiled",
            "starter-script"
        )
    }
configure(libraryProjects) {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
    apply(plugin = "io.deepmedia.tools.deployer")
    apply(plugin = "org.jetbrains.dokka")

    version = rootProject.version

    val sourcesJar by tasks.creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }
    val javadocs = tasks.register<Jar>("dokkaJavadocJar") {
        dependsOn(tasks.dokkaJavadoc)
        from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
        archiveClassifier.set("javadoc")
    }

    deployer {
        val projectUrl: String by project
        val artifactVersion: String by project
        val autherName: String by project
        val autherEmail: String by project

        projectInfo {
            name = rootProject.name
            description = rootProject.description
            url = projectUrl
            groupId = artifactGroup
            artifactId = "$artifactIdPrefix-${project.name}"
            scm {
                fromGithub(autherName, artifactIdPrefix)
            }
            license(apache2)
            developer(autherName, autherEmail)
        }
        content {
            component {
                sources(sourcesJar)
                docs(javadocs)
                fromJava()
            }
        }
        release {
            release.version = artifactVersion
            release.tag = artifactVersion
            release.description = project.description
        }
        centralPortalSpec {
            auth.user = secret("CENTRAL_PORTAL_USER")
            auth.password = secret("CENTRAL_PORTAL_PASSWORD")
            signing.key = secret("SIGNING_KEY")
            signing.password = secret("SIGNING_PASSWORD")

            // Publish manually from this link https://central.sonatype.com/publishing
            allowMavenCentralSync = false
        }
        localSpec {}
    }
}

dependencies {}
