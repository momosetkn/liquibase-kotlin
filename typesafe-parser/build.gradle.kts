val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val kotlinVersion = rootProject.properties["kotlinVersion"] as String
val liquibaseKotlinVersion = rootProject.properties["liquibaseKotlinVersion"] as String
val classgraphVersion = rootProject.properties["classgraphVersion"] as String

dependencies {
    // liquibase-kotlin
    api(project(":dsl"))
    // liquibase
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    // reflection
    api("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    // Scan classes
    api("io.github.classgraph:classgraph:$classgraphVersion")

    // test
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
}
