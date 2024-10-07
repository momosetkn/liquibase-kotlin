dependencies {
    api(project(":dsl"))
    api(project(":compiled-serializer"))
    api(project(":compiled-parser"))
    api(project(":client"))
}

tasks.test {
    useJUnitPlatform()
}
