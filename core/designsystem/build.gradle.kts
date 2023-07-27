plugins {
    id("animelist.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.mumbicodes.designsystem"

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
    val composeBom = platform(libs.compose.bom)

    implementation(libs.androidx.appCompat)
    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)
    implementation(composeBom)

    // Enables formatting rule set in detekt
    detektPlugins(libs.detekt.formatting)
}