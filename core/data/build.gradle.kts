plugins {
    id("animelist.android.library")
    id("animelist.android.hilt")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.mumbicodes.data"

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

    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:local"))

    // Enables formatting rule set in detekt
    detektPlugins(libs.detekt.formatting)

    // ApolloGraphql
    implementation(libs.apollographql)
    implementation(libs.apollographql.inMemoryCache)
}