rootProject.name = "OptiSoftKMP"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":composeApp")

//include(":designsystem")
//include(":data")
//include(":build-logic")
//include(":model")
//include(":feature:login")
//include(":feature:forgotpassword")
//include(":feature:home")
//include(":feature:orders")
//include(":feature:doctors")
include(":common")
include(":feature:doctors")
//include(":core:network")
include(":core:network")
include(":core:di")
