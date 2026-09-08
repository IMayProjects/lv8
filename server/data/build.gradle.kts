import org.jetbrains.exposed.v1.plugin.core.migration.VersionFormat

plugins {
    id("java-library")
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.koin.compiler)
    id("org.jetbrains.exposed.plugin") version "1.5.0"
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

exposed {
    migrations {
        tablesPackage.set("za.org.ecdoe.data.schema.tables")
        databaseUrl.set(System.getenv("PGSQL_URL"))
        databaseUser.set(System.getenv("PGSQL_USR"))
        databasePassword.set(System.getenv("PGSQL_PW"))

        classpath = sourceSets.main.get().runtimeClasspath
        fileDirectory.set(layout.projectDirectory.dir("src/main/res/db/migrations"))
        filePrefix.set("V")
        fileVersionFormat.set(VersionFormat.MAJOR_MINOR)
        fileSeparator.set("__")
        useUpperCaseDescription.set(true)
        fileExtension.set(".sql")
    }
}

dependencies {
    api(project(":core"))
//    --- Core ---
    implementation(project(":server:security"))
//    --- Server : Security ---
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    testImplementation(libs.koin.test)
//    --- Koin ---
    implementation(libs.logback)
//    --- Logback
    implementation(jetbrainsExposed.core)
    implementation(jetbrainsExposed.dao)
    implementation(jetbrainsExposed.jdbc)
    implementation(libs.postgresql)
    implementation(libs.hikariCP)
    testImplementation(libs.kotlin.test)
//    --- Exposed ---
    implementation(libs.kotlinx.coroutines.core)
//    --- Coroutines ---
}

tasks.named("build") {
    dependsOn("generateMigrations")
}