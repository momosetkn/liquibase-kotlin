import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotestVersion: String by project
val kotlinVersion: String by project
val liquibaseVersion: String by project
val artifactIdPrefix: String by project
val liquibaseKotlinVersion: String by project
val myGroup: String by project

plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    `maven-publish`
    id("io.deepmedia.tools.deployer") version "0.14.0"
    id("org.jetbrains.dokka") version "1.9.20"
}

group = myGroup
version = liquibaseKotlinVersion
description = "Liquibase kotlin(DSL, Wrapper client, ORM integration)"

typealias Base64 = java.util.Base64
fun decodeBase64(s: String): String {
    return Base64.getDecoder().decode(s).let { String(it) }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withSourcesJar()
    withJavadocJar()
}
tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/liquibase/liquibase")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
            freeCompilerArgs.addAll(listOf("-Xjvm-default=all"))
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
        projectInfo {
            val projectUrl: String by project
            val githubUrl: String by project

            name = rootProject.name
            description = rootProject.description
            url = projectUrl
            groupId = myGroup
            artifactId = "$artifactIdPrefix-${project.name}"
            scm {
                connection = "scm:git:$githubUrl"
                developerConnection = "scm:git:$githubUrl"
                url = projectUrl
            }
            license(apache2)
            developer("momosetkn", "hyakkun@gmail.com")
        }
        release {
            val liquibaseKotlinVersion: String by project
            release.version = liquibaseKotlinVersion
            release.tag.set("v${liquibaseKotlinVersion}")
            release.description.set("$artifactIdPrefix ${project.name} ${release.tag}")
        }

        centralPortalSpec {
            auth.user.set(secret("CENTRAL_PORTAL_USER"))
            auth.password.set(secret("CENTRAL_PORTAL_PASSWORD"))
        }
        signing {
            key.set(secret("SIGNING_KEY"))
            password.set(secret("SIGNING_PASSWORD"))
        }
    }
}

dependencies {}
