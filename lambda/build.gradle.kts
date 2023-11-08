plugins {
    kotlin("jvm") version "1.9.20"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("plugin.serialization") version "1.9.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))
    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation("com.expediagroup:graphql-kotlin-schema-generator:7.0.2")
    implementation("com.expediagroup:graphql-kotlin-server:7.0.2")
    implementation("org.postgresql:postgresql:42.6.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("io.bouckaert.quicksa1.lambda.ServePDF")
}

kotlin {
    jvmToolchain(17)
}