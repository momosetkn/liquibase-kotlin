import java.util.Properties

val kotestVersion = rootProject.properties["kotestVersion"] as String
val slf4jVersion = rootProject.properties["slf4jVersion"] as String
val kotlinVersion = rootProject.properties["kotlinVersion"] as String
// liquibase-kotlin-client is tested snapshot or latest-stable version
val liquibaseVersion = getSnapshotOrDefaultVersion("liquibaseVersion")

fun loadDefaultProperty(propertyName: String): String {
    val gradleProps = Properties()
    val gradlePropertiesFile = rootProject.projectDir.resolve("gradle.properties")
    gradlePropertiesFile.inputStream().use { gradleProps.load(it) }
    return gradleProps.getProperty(propertyName)
}

fun getSnapshotOrDefaultVersion(propertyName: String): String {
    val version = rootProject.properties[propertyName] as String
    return if (version.any { it.isLetter() }) {
        // snapshot
        version
    } else {
        // latest-stable
        loadDefaultProperty(propertyName)
    }
}

dependencies {
    // liquibase
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
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
