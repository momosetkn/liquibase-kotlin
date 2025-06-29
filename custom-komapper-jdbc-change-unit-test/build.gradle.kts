val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val slf4jVersion = rootProject.properties["slf4jVersion"] as String
val komapperVersion = rootProject.properties["komapperVersion"] as String
val log4jSlf4j2Version = rootProject.properties["log4jSlf4j2Version"] as String
val log4jApiKotlinVersion = rootProject.properties["log4jApiKotlinVersion"] as String
val kotlinVersion = rootProject.properties["kotlinVersion"] as String

dependencies {
    implementation(project(":custom-komapper-jdbc-change"))

    // log
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jSlf4j2Version")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:$log4jApiKotlinVersion")

    // komapper
    // Test for the case with a single dialect
    implementation("org.komapper:komapper-dialect-postgresql-jdbc:$komapperVersion")

    // test
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")

    // db-migration
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    // reflection
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
}
