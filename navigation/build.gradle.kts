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
    composeOptions {
        kotlinCompilerExtensionVersion = Config.composeCompilerExtensionVersion
    }
}

dependencies {

    implementation(project(":common"))
    implementation(project(":domain"))

    // Core
    implementation(dependencyNotation = Dependencies.kotlinCoreKtx)
    api(dependencyNotation = Dependencies.composeNavigation)

    // Dagger Hilt
    implementation(dependencyNotation = Dependencies.hiltAndroid)
    kapt(dependencyNotation = Dependencies.hiltCompiler)

    // Testing
    testImplementation(dependencyNotation = Dependencies.junit)
    androidTestImplementation(dependencyNotation = Dependencies.androidJunit)
    androidTestImplementation(dependencyNotation = Dependencies.espressoCore)
}