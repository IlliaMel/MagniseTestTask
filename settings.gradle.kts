rootProject.name = "MagniseTestTask"
include(":app")
include(":finance")

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
        flatDir {
            dirs("libs")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
          maven {
            url = uri("https://www.myget.org/F/abtsoftware/maven")
        }
    }
}