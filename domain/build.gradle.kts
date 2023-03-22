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

    testImplementation(kotlin(Dependencies.junit5))
    testImplementation(platform(Dependencies.junitPlatform))
    testImplementation(Dependencies.jupiter)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}