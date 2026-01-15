plugins {
    id("java-library")
}

allprojects {
    group = "dev.ewwegg"
    version = "0.0.1-SNAPSHOT"
}

subprojects {
    apply(plugin = "java-library")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release = 25
        }

        test {
            useJUnitPlatform()
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }
}
