plugins {

    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ktor) apply false
}

tasks.register("generateLibraryManifest") {
    group = "documentation"
    description =
        "Generates a manifest of all libraries used across all modules."

    doLast {
        val manifestFile = File(rootDir, "LIBRARY_MANIFEST.md")
        val sb = StringBuilder()
        sb.append("# Project Library Manifest\n\n")
        sb.append("Generated on: ${java.time.LocalDateTime.now()}\n\n")

        // Iterate through all modules in the project
        subprojects {
            val moduleName = this.name
            sb.append("## Module: :$moduleName\n")

            // Extract dependencies from all configurations (implementation, api, etc.)
            val dependenciesList = mutableSetOf<String>()
            configurations.forEach { config ->
                config.dependencies.forEach { dep ->
                    if (dep.group != null) {
                        dependenciesList.add("- ${dep.group}:${dep.name}:${dep.version ?: "unspecified"}")
                    }
                }
            }

            if (dependenciesList.isEmpty()) {
                sb.append("*No external library dependencies.*\n\n")
            } else {
                dependenciesList.sorted().forEach { sb.append("$it\n") }
                sb.append("\n")
            }
        }

        manifestFile.writeText(sb.toString())
        logger.lifecycle("Library manifest successfully updated at: ${manifestFile.absolutePath}")
    }
}

// Hook into the IDE sync lifecycle
tasks.matching { it.name == "prepareKotlinIdeaImport" }.configureEach {
//    dependsOn("generateLibraryManifest")
}

// Fallback anchor for non-Kotlin or standard configuration phases
gradle.projectsEvaluated {
    tasks.findByName("generateLibraryManifest")?.let { task ->
        // Executes the task logic during project evaluation/sync phase
        (task as Task).actions.forEach { it.execute(task) }
    }
}

