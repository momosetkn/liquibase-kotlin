val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val liquibaseKotlinVersion = rootProject.properties["liquibaseKotlinVersion"] as String
val kotlinVersion = rootProject.properties["kotlinVersion"] as String
val komapperVersion = "3.0.0"

dependencies {
    implementation(project(":dsl"))
    implementation(project(":script-parser"))
    // liquibase
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    // komapper
    implementation("org.komapper:komapper-core:$komapperVersion")
    implementation("org.komapper:komapper-jdbc:$komapperVersion")
    // reflection
    api("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    // test
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
}
