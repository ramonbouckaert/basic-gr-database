plugins {
    kotlin("multiplatform") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
}

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core-js:2.3.5")
                implementation("io.ktor:ktor-utils-js:2.3.5")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.639")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.639")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.11.1-pre.639")
            }
        }
    }
}