plugins {
    id("animelist.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    id("animelist.android.hilt")
}

android {
    namespace = "com.mumbicodes.local"
}

dependencies {
    // Preference Datastore
    implementation(libs.preference.datastore)

    // Enables formatting rule set in detekt
    detektPlugins(libs.detekt.formatting)
}