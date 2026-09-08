plugins {
    id("java-library")
    alias(libs.plugins.kotlinJvm)
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

dependencies {
    api(project(":core"))
//    --- Core ---
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    testImplementation(libs.koin.test)
//    --- Koin ---
    api(libs.ktor.server.auth)
    api(libs.ktor.server.auth.jwt)
//    --- Ktor ---
    implementation(libs.argon2.jvm)
//    --- Argon2 ---
    implementation(libs.kotlinx.coroutines.core)
//    --- Coroutines ---
}