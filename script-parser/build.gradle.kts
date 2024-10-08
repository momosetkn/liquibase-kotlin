val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val kotlinVersion = rootProject.properties["kotlinVersion"] as String
val liquibaseKotlinVersion = rootProject.properties["liquibaseKotlinVersion"] as String
val slf4jVersion = rootProject.properties["slf4jVersion"] as String

dependencies {
    // liquibase-kotlin
    api(project(":dsl"))
    // liquibase
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    // kotlin-script
    api("org.jetbrains.kotlin:kotlin-scripting-common:$kotlinVersion")
    api("org.jetbrains.kotlin:kotlin-scripting-jvm:$kotlinVersion")
    api("org.jetbrains.kotlin:kotlin-scripting-jvm-host:$kotlinVersion")
    api("org.jetbrains.kotlin:kotlin-scripting-dependencies:$kotlinVersion")
    api("org.jetbrains.kotlin:kotlin-scripting-dependencies-maven:$kotlinVersion")
    api("org.jetbrains.kotlin:kotlin-script-util:1.8.22")
    // log
    api("org.slf4j:slf4j-api:$slf4jVersion")

    // test
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

tasks.javadoc {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
}
