import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import nu.studer.gradle.jooq.JooqEdition.OSS
import org.jooq.meta.jaxb.Logging.WARN
import org.jooq.meta.jaxb.Property

val dbUrl: String = "jdbc:postgresql://dev-database.cdx4sunf7zls.eu-west-1.rds.amazonaws.com:5432/rx_test?user=postgres&password=aVDB6nh7T2sRT10006pG&useSSL=false'"
val jooqVersion = "3.15.1"

plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    id("nu.studer.jooq") version "6.0.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

    // db
    //runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("io.r2dbc:r2dbc-postgresql:0.8.12.RELEASE")
    jooqGenerator("org.postgresql:postgresql:42.2.14")
    implementation("org.jooq:jooq-codegen:$jooqVersion")
    implementation("org.jooq:jooq-meta:$jooqVersion")
}

jooq {
    version.set(jooqVersion)
    edition.set(OSS)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)

            jooqConfiguration.apply {
                logging = WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = dbUrl
                    properties.add(Property().withKey("ssl").withValue("false"))
                }
                generator.apply {
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                    }
                    target.apply {
                        packageName = "com.example.jooq"
                        directory = "build/generated-src/jooq"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
