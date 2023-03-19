plugins {
    id(Plugins.library)
    id(Plugins.hilt)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
    kotlin(Plugins.serialization) version Versions.kotlin_gradle_version
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk

        testInstrumentationRunner = Config.testRunner
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isRenderscriptDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = Config.jvmTargetVersion
    }
}

dependencies {

    // Kotlin
    implementation(dependencyNotation = Dependencies.kotlinCoreKtx)
    implementation(dependencyNotation = Dependencies.kotlinSerialization)

    // Dagger Hilt
    implementation(dependencyNotation = Dependencies.hiltAndroid)
    implementation(dependencyNotation = Dependencies.hiltCompose)
    kapt(dependencyNotation = Dependencies.hiltCompiler)

    // Network
    implementation(dependencyNotation = Dependencies.retrofit)
    implementation(dependencyNotation = Dependencies.kotlinSerializationConverter)
    implementation(dependencyNotation = Dependencies.loggingInterceptor)
    implementation(dependencyNotation = Dependencies.jsoup)

    // Tests
    testApi(dependencyNotation = Dependencies.junit)
    androidTestApi(dependencyNotation = Dependencies.androidJunit)
}