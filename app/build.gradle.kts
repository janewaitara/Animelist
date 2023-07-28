plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    id("animelist.android.hilt")
}

android {
    namespace = "com.mumbicodes.animelist"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.mumbicodes.animelist"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))

    implementation(project(":feature:home"))
    implementation(project(":feature:search"))
    implementation(project(":feature:yourList"))
    implementation(project(":feature:anime"))
    implementation(project(":feature:character"))

    val composeBom = platform(libs.compose.bom)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.bundles.compose)
    implementation(composeBom)

    // Accompanist
    implementation(libs.accompanist)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.androidx.compose.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.manifest)

    // Enables formatting rule set in detekt
    detektPlugins(libs.detekt.formatting)

    // Work - solves the issue = Targeting S+ (version 31 and above) requires that one of FLAG_IMMUTABLE
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // Hilt
    implementation(libs.hilt.navigation)
    implementation(libs.hilt.work)
}