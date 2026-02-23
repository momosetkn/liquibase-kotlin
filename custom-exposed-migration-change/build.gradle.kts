val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val exposedVersion = rootProject.properties["exposedVersion"] as String

dependencies {
    implementation(project(":dsl"))
    implementation(project(":script-parser"))
    api(project(":custom-change-core"))
    // liquibase
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    // exposed
    implementation("org.jetbrains.exposed:exposed-migration-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-migration-jdbc:$exposedVersion")

    // test
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
}
