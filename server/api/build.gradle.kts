plugins {
    id("java-library")
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies{
    api(project(":core"))
//    --- Core ---
    implementation(project(":server:security"))
//    --- Server : Security ---
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.serialization.json)
//    --- Serialization ---
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    testImplementation(libs.koin.test)
//    --- Koin ---
    implementation(libs.kotlinx.coroutines.core)
//    --- Coroutines ---
}