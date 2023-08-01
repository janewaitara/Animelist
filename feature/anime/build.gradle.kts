plugins {
    id("animelist.android.library")
    id("animelist.android.library.compose")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    id("animelist.android.hilt")
}

android {
    namespace = "com.mumbicodes.anime"

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
    implementation(project(":core:model"))

    implementation(libs.androidx.appCompat)

    // Enables formatting rule set in detekt
    detektPlugins(libs.detekt.formatting)
}