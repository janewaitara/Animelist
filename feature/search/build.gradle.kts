plugins {
    id("animelist.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    id("animelist.android.hilt")
}

android {
    namespace = "com.mumbicodes.search"

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
    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))

    val composeBom = platform(libs.compose.bom)

    implementation(libs.androidx.appCompat)

    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)
    implementation(composeBom)

    // lifecycle
    implementation(libs.bundles.lifecycle)

    // Hilt
    implementation(libs.hilt.navigation)

    // Enables formatting rule set in detekt
    detektPlugins(libs.detekt.formatting)
}