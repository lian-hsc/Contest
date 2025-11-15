pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "Contest"
include("Core")
include("CCC2023Autumn")
include("CatCoderTraining")
include("AdventOfCode2023")
include("AdventOfCode2022")
include("CCC2024Spring")
include("CCC2024Autumn")
include("CCC2025Autumn")
include("AdventOfCode2024")
