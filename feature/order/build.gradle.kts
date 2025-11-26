plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

kotlin {

    androidLibrary {
        namespace = "cl.optisoft.order"
        compileSdk = 36
        minSdk = 24

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }

    // iOS Targets
    val xcfName = "feature:orderKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
            export(project(":core:designsystem"))
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
            export(project(":core:designsystem"))
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
            export(project(":core:designsystem"))
        }
    }

    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        commonMain {
//            resources.srcDirs("src/commonMain/composeResources")

            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.material3AdaptiveNavigationSuite)
                implementation(libs.jetbrains.compose.material3.adaptive.navigation)

                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)

                implementation(libs.kermit)
                implementation(libs.kotlinx.serialization.json)

                implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.1")

                implementation(project(":core:network"))
                implementation(project(":core:designsystem"))
                implementation(project(":common"))
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
//            resources.srcDirs("src/commonMain/composeResources")
            dependencies {

                implementation(libs.ktor.client.okhttp)
                implementation(libs.androidx.ui.tooling)
                implementation(compose.components.uiToolingPreview)
                implementation("androidx.customview:customview-poolingcontainer:1.0.0")
            }
        }

        iosMain {
            // 👇 **ESTO ES LO IMPORTANTE PARA QUE IOS LEA LAS IMÁGENES**
            resources.srcDirs(
                "../core/designsystem/src/commonMain/composeResources"
            )

            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)
            }
        }
    }

}