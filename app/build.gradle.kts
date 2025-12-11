plugins {
    // Apply the shared build logic from a convention plugin.
    // The shared code is located in `buildSrc/src/main/kotlin/kotlin-jvm.gradle.kts`.
    id("buildsrc.convention.kotlin-jvm")

    // Apply the Application plugin to add support for building an executable JVM application.
    application
}

dependencies {
    // Project "app" depends on project "utils". (Project paths are separated with ":", so ":utils" refers to the top-level "utils" project.)
//    implementation(project(":utils")) TODO move utils to this module
    implementation(libs.bundles.kotlinxEcosystem)
    testImplementation(kotlin("test"))
    implementation("org.jacop:jacop:4.10.0")
    implementation("tools.aqua:z3-turnkey:4.14.1")
    implementation("org.json:json:20231013")
}

application {
    // Define the Fully Qualified Name for the application main class
    // (Note that Kotlin compiles `App.kt` to a class with FQN `com.example.app.AppKt`.)
    mainClass = "org.aoc.app.AppKt"
}
