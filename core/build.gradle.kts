plugins {
    id("com.gradleup.shadow") version "9.0.0"
}

dependencies {
    // Internal API dependency
    implementation(project(":api"))

    // Hytale Server API (provided by server at runtime)
    compileOnly(files("../libs/hytale-server.jar"))

    // Common dependencies (bundled in JAR)
    implementation("com.google.code.gson:gson:2.11.0")
}

tasks {
    shadowJar {
        archiveBaseName.set("capital")
        archiveClassifier.set("")
        archiveVersion.set("")

        // Relocate dependencies to avoid conflicts
        relocate("com.google.gson", "dev.ewwegg.capital.libs.gson")

        // Minimize JAR size
        minimize()
    }

    build {
        dependsOn(shadowJar)
    }
}
