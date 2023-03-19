import java.io.FileInputStream
import java.util.Properties

plugins {
    id(Plugins.application)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
    id(Plugins.hilt)
    id(Plugins.googleServices)
}

val keyStorePropertiesFile = rootProject.file("keystore.properties")

val keyStoreProperties = Properties()

keyStoreProperties.load(FileInputStream(keyStorePropertiesFile))

android {
    signingConfigs {
        create("release") {
            keyAlias = keyStoreProperties.getProperty("keyAlias") as String
            storeFile = file(keyStoreProperties.getProperty("storeFile") as String)
            storePassword = keyStoreProperties.getProperty("storePassword") as String
            keyPassword = keyStoreProperties.getProperty("keyPassword") as String
        }
    }
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.testRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            isRenderscriptDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        create("benchmark") {
            initWith(getByName("release"))
            signingConfig = signingConfigs.getByName("debug")

            matchingFallbacks += listOf("release")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
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

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":data:repository"))
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(project(":features:on_boarding"))
    implementation(project(":features:authorization"))
    implementation(project(":features:home"))
    implementation(project(":features:alarm"))
    implementation(project(":navigation"))

    // Core
    implementation(dependencyNotation = Dependencies.kotlinCoreKtx)
    coreLibraryDesugaring(dependencyNotation = Dependencies.desugar)

    implementation(dependencyNotation = "androidx.core:core-splashscreen:1.0.0")

    // Accompanist
    implementation(dependencyNotation = Dependencies.systemUiController)

    // Dagger Hilt
    implementation(dependencyNotation = Dependencies.hiltAndroid)
    implementation(dependencyNotation = Dependencies.hiltCompose)
    kapt(dependencyNotation = Dependencies.hiltCompiler)

    // Testing
    testImplementation(dependencyNotation = Dependencies.junit)
    androidTestImplementation(dependencyNotation = Dependencies.androidJunit)
    androidTestImplementation(dependencyNotation = Dependencies.espressoCore)
    androidTestImplementation(dependencyNotation = Dependencies.junitCompose)
}