repositories {
    mavenCentral()
}

dependencies {
    // modules
    api(project(":dsl"))
    api(project(":parser"))
    api(project(":serializer"))
}

tasks.test {
    useJUnitPlatform()
}
