plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

kotlin {

    // Android Target
    androidLibrary {
        namespace = "cl.optisoft.doctors"
        compileSdk = 36
        minSdk = 24


        withHostTestBuilder { }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }

    // iOS Targets
    val xcfName = "feature:doctorsKit"

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
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.all {
            freeCompilerArgs += "-Xapple-platform-version-min=15.0"
        }
    }

    // Source Sets
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
//                implementation(compose.material3.pullrefresh)
//                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
                implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.1")
                implementation("org.jetbrains.compose.foundation:foundation")
                implementation("org.jetbrains.compose.foundation:foundation-layout")
                implementation("org.jetbrains.compose.animation:animation")
                implementation("io.coil-kt.coil3:coil-compose:3.0.0")
//                implementation("androidx.emoji2:emoji2")
//                implementation("org.jetbrains.compose.ui:ui-uikit")
                implementation("media.kamel:kamel-image:0.9.3")
                implementation("androidx.emoji2:emoji2:1.4.0")
                implementation("androidx.emoji2:emoji2-views:1.4.0")
                implementation("androidx.emoji2:emoji2-views-helper:1.4.0")
                implementation("io.github.ismoy:imagepickerkmp:1.0.27")
                implementation(project(":core:network"))
                implementation(project(":core:designsystem"))
                implementation(project(":common"))
//                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
                implementation("network.chaintech:kmp-date-time-picker:1.1.1")
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

//
//  COMPOSE RESOURCES CONFIG
//
//compose.resources {
//    publicResClass = true
//    packageOfResClass = "cl.optisoft.doctors.resources"
//    // packageName = "cl.optisoft.doctors.resources" // (opcional)
//}


