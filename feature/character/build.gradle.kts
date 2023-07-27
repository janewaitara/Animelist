plugins {
    id("animelist.android.library")
    id("animelist.android.hilt")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.mumbicodes.character"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:common"))

    implementation(libs.androidx.appCompat)

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose)

    // Accompanist
    implementation(libs.accompanist)

    // Hilt
    implementation(libs.hilt.navigation)

    // Enables formatting rule set in detekt
    detektPlugins(libs.detekt.formatting)
}