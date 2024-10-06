val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val jooqVersion = rootProject.properties["jooqVersion"] as String

dependencies {
    implementation(project(":dsl"))
    implementation(project(":script-parser"))
    api(project(":custom-change-core"))
    // liquibase
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    // jooq
    implementation("org.jooq:jooq:$jooqVersion")

    // test
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
}
