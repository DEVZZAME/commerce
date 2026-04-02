import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("kapt") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.13"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "zzame"
version = "0.0.1-SNAPSHOT"
description = "commerce"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kapt {
    correctErrorTypes = true
}

kotlin {
    jvmToolchain(17)

    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

val envFile = rootProject.file(".env")
val envProperties = Properties().apply {
    if (envFile.exists()) {
        envFile.inputStream().use(::load)
    }
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    envProperties.forEach { key, value ->
        environment(key.toString(), value.toString())
    }
}
