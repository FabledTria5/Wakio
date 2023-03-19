apply {
    plugin(Plugins.gradleVersions)
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(dependencyNotation = Dependencies.gradle)
        classpath(dependencyNotation = Dependencies.kotlinGradlePlugin)
        classpath(dependencyNotation = Dependencies.hiltAndroidPlugin)
        classpath(dependencyNotation = Dependencies.googleServices)
        classpath(dependencyNotation = Dependencies.gradleDependencies)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }
}