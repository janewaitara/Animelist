plugins {
    id("animelist.android.library")
    id("animelist.android.hilt")
    id("animelist.android.library.compose")
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
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:common"))

    implementation(libs.androidx.appCompat)

    // Accompanist
    implementation(libs.accompanist)

    // Enables formatting rule set in detekt
    detektPlugins(libs.detekt.formatting)
}