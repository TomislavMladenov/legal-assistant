val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.9"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

group = "paralegal.mike.com"
version = "0.0.1"

application {
    mainClass.set("paralegal.mike.com.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-openapi")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("org.apache.pdfbox:pdfbox:2.0.30")

    // Import Kotlin API client BOM
    implementation(platform("com.aallam.ktoken:ktoken-bom:0.3.0"))
    // Define dependencies without versions
    implementation("com.aallam.ktoken:ktoken")
    runtimeOnly("io.ktor:ktor-client-okhttp")

    // import Kotlin API client BOM
    implementation(platform("com.aallam.openai:openai-client-bom:3.7.0"))
    // define dependencies without versions
    implementation("com.aallam.openai:openai-client")

    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
