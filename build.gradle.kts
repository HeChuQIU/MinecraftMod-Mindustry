import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.*

plugins {
    idea
    kotlin("jvm") version "2.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("com.github.jakemarsden.git-hooks") version "0.0.2"
    id("net.minecraftforge.gradle") version "[6.0,6.2)"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    id("org.spongepowered.mixin") version "0.7.+"
}

repositories {
    maven("https://maven.aliyun.com/repository/public/")
    maven("https://maven.aliyun.com/repository/gradle-plugin/")
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/") {
        name = "GeckoLib"
    }
    maven("https://dvs1.progwml6.com/files/maven/") {
        name = "Progwml6's maven"
    }
    maven("https://maven.blamejared.com/") {
        name = "Jared's maven"
    }
    maven("https://modmaven.dev") {
        name = "ModMaven"
    }
    maven("https://maven.zerono.it/") {
        name = "zerono"
    }
    maven("https://cursemaven.com") {
        content {
            includeGroup("curse.maven")
        }
    }
    mavenLocal()
    mavenCentral()
}

jarJar.enable()

val minecraftVersion: String by project
val forgeVersion: String by project
val modId: String by project
val modVersion: String by project
val modGroupId: String by project

group = modGroupId
version = "$modVersion${getVersionMetadata()}"
val targetJavaVersion = 17

dependencies {
    val jeiVersion: String by project
    val geckolibVersion: String by project
    val createId: String by project
    val betterF3Id: String by project
    val clothConfigAPIId: String by project
    val jadeId: String by project
    val justEnoughCharactersId: String by project
    val kiwiFileId: String by project
    minecraft("net.minecraftforge:forge:$minecraftVersion-$forgeVersion")
    compileOnly(fg.deobf("mezz.jei:jei-$minecraftVersion-common-api:$jeiVersion"))
    compileOnly(fg.deobf("mezz.jei:jei-$minecraftVersion-forge-api:$jeiVersion"))
    runtimeOnly(fg.deobf("mezz.jei:jei-${minecraftVersion}-forge:${jeiVersion}"))
    implementation(fg.deobf("software.bernie.geckolib:geckolib-forge-${minecraftVersion}:${geckolibVersion}"))
    implementation(fg.deobf("curse.maven:create-328085:${createId}"))
    implementation(fg.deobf("curse.maven:BetterF3-401648:${betterF3Id}"))
    implementation(fg.deobf("curse.maven:ClothConfigAPI-348521:${clothConfigAPIId}"))
    implementation(fg.deobf("curse.maven:jade-324717:${jadeId}"))
    implementation(fg.deobf("curse.maven:just-enough-characters-250702:${justEnoughCharactersId}"))
    implementation(fg.deobf("curse.maven:kiwi-303657:${kiwiFileId}"))
    implementation("org.apache.commons:commons-io:1.3.2")
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
}

minecraft {
    val mappingChannel: String by project
    val mappingVersion: String by project
    mappings(mappingChannel, mappingVersion)
    enableIdeaPrepareRuns.set(true)
    copyIdeResources.set(true)
    accessTransformers(file("src/main/resources/META-INF/accesstransformer.cfg"))
    runs {
        configureEach {
            workingDirectory(project.file("run"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            mods {
                create(modId) {
                    source(sourceSets.main.get())
                }
            }
        }

        create("client") {
            property("forge.enabledGameTestNamespaces", modId)
            args("--mixin.config=mixins.mindustry.json")
        }

        create("server") {
            property("forge.enabledGameTestNamespaces", modId)
            args("--mixin.config=mixins.mindustry.json", "--nogui")
        }

        create("gameTestServer") {
            property("forge.enabledGameTestNamespaces", modId)
        }

        create("data") {
            args(
                "--mod",
                modId,
                "--all",
                "--output",
                file("src/generated/resources/"),
                "--existing",
                file("src/main/resources/")
            )
        }
    }
}

sourceSets.main {
    resources {
        srcDirs("src/generated/resources")
    }
}

base {
    archivesName.set(modId)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
}

kotlin {
    jvmToolchain(targetJavaVersion)
}

mixin {
    add(sourceSets.main.get(), "mixins.mindustry.json")
}

tasks {
    val modName: String by project
    val modLicense: String by project
    val modAuthors: String by project
    val modDescription: String by project
    val minecraftVersionRange: String by project
    val forgeVersionRange: String by project
    val loaderVersionRange: String by project

    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

    withType<KotlinCompile>().configureEach {
        compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
    }

    processResources {
        val resourceTargets = mutableListOf("META-INF/mods.toml", "pack.mcmeta")
        val replaceProperties = mutableMapOf(
            "minecraft_version" to minecraftVersion,
            "minecraft_version_range" to minecraftVersionRange,
            "forge_version" to forgeVersion,
            "forge_version_range" to forgeVersionRange,
            "loader_version_range" to loaderVersionRange,
            "mod_id" to modId,
            "mod_name" to modName,
            "mod_license" to modLicense,
            "mod_version" to modVersion,
            "mod_authors" to modAuthors,
            "mod_description" to modDescription
        )

        inputs.properties(replaceProperties)

        filesMatching(resourceTargets) {
            expand(replaceProperties)
        }
    }

    jar {
        finalizedBy("reobfJar")
        manifest {
            attributes(
                "Build-By" to System.getProperty("user.name"),
                "Build-TimeStamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(Date()),
                "Build-Version" to version,
                "Version-Type" to getVersionMetadata(),
                "Created-By" to "Gradle ${gradle.gradleVersion}",
                "Build-Jdk" to "${System.getProperty("java.version")} " +
                        "(${System.getProperty("java.vendor")} ${System.getProperty("java.vm.version")})",
                "Build-OS" to "${System.getProperty("os.name")} " +
                        "${System.getProperty("os.arch")} ${System.getProperty("os.version")}",
                "Specification-Title" to modId,
                "Specification-Vendor" to modAuthors,
                "Specification-Version" to "1",
                "Implementation-Title" to project.name,
                "Implementation-Vendor" to modAuthors,
                "MixinConfigs" to "mixins.mindustry.json",
                "FMLAT" to "accesstransformer.cfg"
            )
        }
    }
}

// code style check for kotlin
detekt {
    buildUponDefaultConfig = true
    autoCorrect = true
    config.setFrom(rootProject.files("detekt.yml"))
}

// git hooks for automatically checking code style
gitHooks {
    setHooks(
        mapOf("pre-commit" to "detekt")
    )
}

fun getVersionMetadata(): String {
    val buildId = System.getenv("GITHUB_RUN_NUMBER")
    val workflow = System.getenv("GITHUB_WORKFLOW")
    val release = System.getenv("RELEASE")

    if (workflow == "Release" || !release.isNullOrBlank()) {
        return ""
    }

    // CI builds only
    if (!buildId.isNullOrBlank()) {
        return "+build.$buildId"
    }

    // No tracking information could be found about the build
    return "+nightly.${(System.currentTimeMillis() % 1e6).toInt().toString().padStart(6, '0')}"
}
