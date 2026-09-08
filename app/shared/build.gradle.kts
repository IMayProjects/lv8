import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room3)
    alias(libs.plugins.koin.compiler)

}

kotlin {
    jvm()

    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class) wasmJs {
        browser()
    }

    android {
        namespace = "za.org.ecdoe.elevate.app.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
// --- Default ---
            implementation(libs.androidx.sqlite.bundled.android)
            implementation(libs.androidx.room3.runtime.android)
            implementation(libs.androidx.room3.sqlite.wrapper)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            api(project(":core"))
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
// --- Default ---
            implementation(libs.androidx.room3.runtime)
//            --- Room ---
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
            implementation(libs.koin.compose)
            implementation(libs.koin.viewmodel)
//            --- Koin ---
            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.jetbrains.navigation3.lifecycle.viewmodel)
//            --- Compose Navigation 3

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
        }
        jvmMain.dependencies {
            implementation(libs.androidx.sqlite.bundled.jvm)
        }
        jsMain.dependencies {
            implementation(libs.wrappers.browser)
            implementation(libs.androidx.sqlite.web)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
//    --- Default ---
    add("kspAndroid", libs.androidx.room3.compiler)
    add("kspJvm", libs.androidx.room3.compiler)
    add("kspJs", libs.androidx.room3.compiler)
    add("kspWasmJs", libs.androidx.room3.compiler)
//    --- Room ---

}

room3{
    schemaDirectory("$projectDir/schemas")
}

koinCompiler{
    userLogs=true
    debugLogs=false
    unsafeDslChecks=true
}