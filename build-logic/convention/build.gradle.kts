plugins {
    `kotlin-dsl`
}

group = "com.mumbicodes.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "animelist.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidHilt"){
            id = "animelist.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidLibraryCompose"){
            id = "animelist.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
    }
}