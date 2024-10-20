val kotestVersion = rootProject.properties["kotestVersion"] as String
val slf4jVersion = rootProject.properties["slf4jVersion"] as String
val kotlinVersion = rootProject.properties["kotlinVersion"] as String
val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String

dependencies {
    // liquibase
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    api(project(":liquibase-internal"))
    // log
    api("org.slf4j:slf4j-api:$slf4jVersion")

    // test
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    // reflection
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
}
