dependencies {
    api(project(":dsl"))
    api(project(":script-serializer"))
    api(project(":script-parser"))
    api(project(":client"))
}

tasks.test {
    useJUnitPlatform()
}
