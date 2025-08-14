@Suppress("UNCHECKED_CAST")
val libraryProjects = rootProject.extra["libraryProjects"] as List<Project>

dependencies {
    constraints {
        libraryProjects
            .filter { it.name != project.name } // exclude self
            .forEach {
                api(project(it.path))
            }
    }
}
