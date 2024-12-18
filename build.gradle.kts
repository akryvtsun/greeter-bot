plugins {
    jacoco
    kotlin("jvm") version "2.0.20"
}

version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.api-client:google-api-client:1.23.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.23.0")
    implementation("com.google.apis:google-api-services-sheets:v4-rev493-1.23.0")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("org.assertj:assertj-core:3.11.1")
}

tasks {
    test {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
        // Pass all system properties to the test JVM
        systemProperties = System.getProperties().map { it.key.toString() to it.value }.toMap()
    }

    jacocoTestReport {
        dependsOn(test)

        reports {
            xml.required = true
        }
    }
}
