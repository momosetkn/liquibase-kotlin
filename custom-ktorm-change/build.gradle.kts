val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val liquibaseKotlinVersion = rootProject.properties["liquibaseKotlinVersion"] as String
val kotlinVersion = rootProject.properties["kotlinVersion"] as String
val ktormVersion = rootProject.properties["ktormVersion"] as String

dependencies {
    implementation(project(":dsl"))
    implementation(project(":script-parser"))
    api(project(":custom-change-core"))
    // liquibase
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    // ktorm
    implementation("org.ktorm:ktorm-core:$ktormVersion")

    // test
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.ktorm:ktorm-support-mysql:$ktormVersion")
    testImplementation("org.ktorm:ktorm-support-oracle:$ktormVersion")
    testImplementation("org.ktorm:ktorm-support-postgresql:$ktormVersion")
    testImplementation("org.ktorm:ktorm-support-sqlite:$ktormVersion")
    testImplementation("org.ktorm:ktorm-support-sqlserver:$ktormVersion")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
}
