plugins {
    id("animelist.android.library")
    id("animelist.android.library.compose")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    id("animelist.android.hilt")
}

android {
    namespace = "com.mumbicodes.ui"

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
}

dependencies {
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.appCompat)

    // Enables formatting rule set in detekt
    detektPlugins(libs.detekt.formatting)

    // lifecycle
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.constraintlayout)

    // Media3
    implementation(libs.bundles.media3)
}