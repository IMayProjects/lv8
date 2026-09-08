plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
}

group = "za.org.ecdoe.elevate"
version = "1.0.0"
application {
    mainClass = "za.org.ecdoe.elevate.ApplicationKt"
}

dependencies {
    api(project(":core"))
    implementation(project(":server:api"))
    implementation(project(":server:data"))
    implementation(project(":server:security"))
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
//    --- Default ---
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    testImplementation(libs.koin.test)
//    --- koin ---
    implementation(libs.kotlinx.coroutines.core)
//    --- Coroutines ---


}