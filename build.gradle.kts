plugins {
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    jacoco
}

group = "org.uqbar"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // básicos de cualquier proyecto Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // conexión a la base de datos
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // logging
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    modules {
        module("org.springframework.boot:spring-boot-starter-logging") {
            replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.jacocoTestReport {
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude("**/config/**", "**/entity/**", "**/*Application*.*", "**/ServletInitializer.*")
            }
        })
    )
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.register("runOnGitHub") {
    dependsOn("jacocoTestReport")
    group = "custom"
    description = "$ ./gradlew runOnGitHub # runs on GitHub Action"
}