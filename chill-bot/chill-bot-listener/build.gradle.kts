import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    //kotlin("plugin.jpa") version "1.9.24"

    id("org.springframework.boot") version "3.2.6"
    id("io.spring.dependency-management") version "1.1.5"

    kotlin("plugin.serialization") version "2.0.0"
}

group = "com.oli"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa
    //implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.0")


    // Dev
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Logger
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")

    // Telegram Bot Libraries, but not from video
    implementation("org.telegram:telegrambots:6.8.0")
    implementation("org.telegram:telegrambots-longpolling:7.2.1")
    implementation("org.telegram:telegrambots-client:7.2.1")
    implementation("org.telegram:telegrambots-springboot-longpolling-starter:7.2.1")

    // AMQP
    // https://mvnrepository.com/artifact/org.springframework.amqp/spring-amqp
    //implementation("org.springframework.amqp:spring-amqp")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-amqp
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    //implementation("org.postgresql:postgresql:42.3.10")
    implementation("org.postgresql:postgresql")

    // https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    // REDIS
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // SERIALIZATION
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.postgresql:postgresql")

    // Test
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.test {
    useJUnitPlatform()
}