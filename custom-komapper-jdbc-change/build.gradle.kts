val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val liquibaseKotlinVersion = rootProject.properties["liquibaseKotlinVersion"] as String
val kotlinVersion = rootProject.properties["kotlinVersion"] as String
val komapperVersion = rootProject.properties["komapperVersion"] as String

dependencies {
    implementation(project(":dsl"))
    implementation(project(":script-parser"))
    api(project(":custom-change-core"))
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
    testImplementation("org.komapper:komapper-dialect-h2-jdbc:$komapperVersion")
    testImplementation("org.komapper:komapper-dialect-mariadb-jdbc:$komapperVersion")
    testImplementation("org.komapper:komapper-dialect-mysql-jdbc:$komapperVersion")
    testImplementation("org.komapper:komapper-dialect-oracle-jdbc:$komapperVersion")
    testImplementation("org.komapper:komapper-dialect-postgresql-jdbc:$komapperVersion")
    testImplementation("org.komapper:komapper-dialect-sqlserver-jdbc:$komapperVersion")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
}
