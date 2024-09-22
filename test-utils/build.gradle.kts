val slf4jVersion = rootProject.properties["slf4jVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val komapperVersion = rootProject.properties["komapperVersion"] as String

dependencies {
    // liquibase-kotlin
    implementation(project(":compiled-parser"))
    // log
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.0")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.5.0")

    // komapper
    implementation("org.komapper:komapper-jdbc:$komapperVersion")

    // test
    implementation(kotlin("test"))
    implementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    implementation("io.kotest:kotest-runner-junit5:$kotestVersion")

    // h2database
    implementation("com.h2database:h2:2.3.232")

    implementation(platform("org.junit:junit-bom:5.10.0"))
    implementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("kotest.framework.classpath.scanning.config.disable", "true")
}
