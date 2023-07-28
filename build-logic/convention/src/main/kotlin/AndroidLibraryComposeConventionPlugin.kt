import com.android.build.api.dsl.LibraryExtension
import com.mumbicodes.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    compose = true
                }
                composeOptions {
                    kotlinCompilerExtensionVersion = "1.4.2"
                }
            }

            dependencies {
                val composeBom = libs.findLibrary("compose.bom").get()

                add("implementation", platform(composeBom))
                add("implementation", libs.findBundle("compose").get())
                add("implementation", libs.findLibrary("compose.navigation").get())
                add("implementation", libs.findLibrary("hilt.navigation").get())
            }
        }
    }
}