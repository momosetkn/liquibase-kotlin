val liquibaseVersion = rootProject.properties["liquibaseVersion"] as String
val kotestVersion = rootProject.properties["kotestVersion"] as String
val slf4jVersion = rootProject.properties["slf4jVersion"] as String
val komapperVersion = rootProject.properties["komapperVersion"] as String
val log4jSlf4j2Version = rootProject.properties["log4jSlf4j2Version"] as String
val log4jApiKotlinVersion = rootProject.properties["log4jApiKotlinVersion"] as String

dependencies {
    implementation(project(":dsl"))
    implementation(project(":script-serializer"))
    implementation(project(":compiled-serializer"))
    implementation(project(":script-parser"))
    implementation(project(":compiled-parser"))
    implementation(project(":command-client"))
    implementation(project(":client"))
    implementation(project(":custom-komapper-jdbc-change"))

    // h2database
    implementation("com.h2database:h2:2.4.240")

    // db-migration
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")

    // komapper
    implementation("org.komapper:komapper-core:$komapperVersion")
    implementation("org.komapper:komapper-jdbc:$komapperVersion")

    // log
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jSlf4j2Version")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:$log4jApiKotlinVersion")

    // extend test
    implementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
}

// To run in a separate process.
tasks.register<JavaExec>("startH2Server") {
    group = "Database"
    description = "Starts the H2 Database TCP server"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("org.h2.tools.Server")
    val port = project.findProperty("port")?.toString() ?: return@register
    // Not daemon. Because, required stay running even after gradle process.
    args = listOf("-tcp", "-tcpAllowOthers", "-tcpPort", port, "-trace", "-ifNotExists")
}
