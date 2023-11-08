plugins {
    kotlin("jvm") version "1.9.20"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))
    implementation("org.liquibase:liquibase-core:4.24.0")
    implementation("com.sksamuel.hoplite:hoplite-core:2.7.2")
    implementation("com.sksamuel.hoplite:hoplite-json:2.7.2")
    implementation("com.tersesystems.logback:logback-classic:1.1.1")
    implementation("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation("org.postgresql:postgresql:42.6.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("io.bouckaert.basicgrdatabase.utility.Main")
}

kotlin {
    jvmToolchain(17)
}