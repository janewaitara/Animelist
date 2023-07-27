plugins {
    id("animelist.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    id("com.apollographql.apollo3") version "3.8.2"
    id("animelist.android.hilt")
}

android {
    namespace = "com.mumbicodes.network"

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

    // Enables formatting rule set in detekt
    detektPlugins(libs.detekt.formatting)

    // ApolloGraphql
    implementation(libs.apollographql)
    implementation(libs.apollographql.inMemoryCache)
}

apollo {
    service("service") {
        packageName.set("com.mumbicodes.network")
    }
}