pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Wakio"
include(":app")
include(":features")
include(":data")
include(":data:remote")
include(":data:local")
include(":data:repository")
include(":domain")
include(":common")
include(":navigation")
include(":features:home")
include(":features:on_boarding")
include(":features:alarm")
include(":features:authorization")
include(":benchmark")
