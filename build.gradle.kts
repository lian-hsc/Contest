import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.0"
}

repositories {
    mavenCentral()
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    group = "me.golendtrio"
    version = "1.0"

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "21"
    }
}

configure(subprojects - project(":Core")) {
    dependencies {
        implementation(project(":Core"))
    }
}

kotlin {
    jvmToolchain(21)
}