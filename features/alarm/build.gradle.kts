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
            isRenderscriptDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = Config.jvmTargetVersion
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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
    implementation(dependencyNotation = Dependencies.constraintLayout)
    implementation(dependencyNotation = Dependencies.uiUtil)
    coreLibraryDesugaring(dependencyNotation = Dependencies.desugar)

    implementation(dependencyNotation = Dependencies.systemUiController)

    // Dagger Hilt
    implementation(dependencyNotation = Dependencies.hiltAndroid)
    implementation(dependencyNotation = Dependencies.hiltCompose)
    kapt(dependencyNotation = Dependencies.hiltCompiler)

    // Testing
    testImplementation(dependencyNotation = Dependencies.junit)
    debugImplementation(dependencyNotation = Dependencies.composeManifest)
    androidTestImplementation(dependencyNotation = Dependencies.androidJunit)
    androidTestImplementation(dependencyNotation = Dependencies.espressoCore)
    androidTestImplementation(dependencyNotation = Dependencies.junitCompose)

}