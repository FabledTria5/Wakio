plugins {
    id(Plugins.library)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
    id(Plugins.hilt)
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = Config.jvmTargetVersion
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Config.composeCompilerExtensionVersion
    }
}

dependencies {

    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":navigation"))

    // Core
    implementation(dependencyNotation = Dependencies.kotlinCoreKtx)

    implementation("com.google.accompanist:accompanist-permissions:0.29.1-alpha")

    // Dagger Hilt
    implementation(dependencyNotation = Dependencies.hiltAndroid)
    kapt(dependencyNotation = Dependencies.hiltCompiler)
}