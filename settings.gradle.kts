pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.parchmentmc.org")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

buildscript {
    repositories {
        maven("https://repo.spongepowered.org/repository/maven-public/")
    }
}

rootProject.name = "mindustry"
