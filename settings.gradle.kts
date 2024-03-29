pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
rootProject.name = "Animelist"
include(":app")
include(":feature:search")
include(":feature:anime")
include(":feature:home")
include(":feature:yourList")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:ui")
include(":core:designsystem")
include(":core:common")
include(":feature:character")
include(":core:model")
include(":core:local")