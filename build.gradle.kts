plugins {
    kotlin("jvm") version "1.8.21"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("com.willowtreeapps.assertk:assertk:0.27.0")
}

tasks.test {
    useJUnitPlatform()
}

group = "eu.luminis"
version = "1.0-SNAPSHOT"
description = "playing-with-domain-models"
