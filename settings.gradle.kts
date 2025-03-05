pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootDir.run {
    listOf(
        "gradle.properties",
        "gradlew.bat",
        "gradlew",
        "gradle/wrapper/gradle-wrapper.jar",
        "gradle/wrapper/gradle-wrapper.properties"
    ).map { path ->
        resolve(path)
            .copyTo(
                target = rootDir.resolve("outline_sdk").resolve(path),
                overwrite = true
            )
    }
}

rootProject.name = "Outline Media"
include(":app")
include(":out_proxy")
includeBuild("outline_sdk")
