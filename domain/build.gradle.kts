plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = Config.javaVersion
    targetCompatibility = Config.javaVersion
}

dependencies {
    implementation(dependencyNotation = Dependencies.javaInject)
    implementation(dependencyNotation = Dependencies.coroutinesCore)

    testImplementation(dependencyNotation = Dependencies.junit)
}