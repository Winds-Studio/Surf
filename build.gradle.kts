plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "8.3.1"
}

group = "cn.dreeam.surf"
version = "5.0.0"
description = "Fix exploits and remove illegal/NBT items for anarchy servers"

repositories {
    mavenCentral()

    flatDir {
        dirs("./libs")
    }

    // PaperMC
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    // NBT-API
    maven {
        name = "codemc-repo"
        url = uri("https://repo.codemc.io/repository/maven-public/")
    }

    // ConfigurationMaster
    maven {
        name = "cm-repo"
        url = uri("https://ci.pluginwiki.us/plugin/repository/everything/")
    }

    // FoliaLib
    maven {
        name = "devmart-other"
        url = uri("https://nexuslite.gcnt.net/repos/other/")
    }
}

val adventureVersion = "4.17.0"

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation("com.github.thatsmusic99:ConfigurationMaster-API:v2.0.0-rc.2") {
        exclude(group = "org.yaml", module = "snakeyaml")
    }
    implementation("org.bstats:bstats-bukkit:3.0.3")
    implementation("com.tcoded:FoliaLib:0.4.2")
    implementation("com.github.cryptomorin:XSeries:11.2.1")
    implementation("de.tr7zw:item-nbt-api:2.13.2")
    compileOnly(files("libs/RoseStacker-1.5.22.jar"))

    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
    implementation("net.kyori:adventure-api:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-legacy:$adventureVersion")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    build.configure {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveFileName = "${project.name}-${project.version}.${archiveExtension.get()}"
        exclude("META-INF/**") // Dreeam - Avoid to include META-INF/maven in Jar
        relocate("net.kyori", "cn.dreeam.surf.libs.kyori")
        relocate("io.github.thatsmusic99.configurationmaster", "cn.dreeam.surf.libs.configurationmaster")
        relocate("org.bstats", "cn.dreeam.surf.libs.bstats")
        relocate("com.tcoded.folialib", "cn.dreeam.surf.libs.folialib")
        relocate("com.cryptomorin.xseries", "cn.dreeam.surf.libs.xseries")
        relocate("de.tr7zw.changeme.nbtapi", "cn.dreeam.surf.libs.nbtapi")
    }

    processResources {
        filesMatching("**/plugin.yml") {
            expand(
                "version" to project.version,
                "description" to project.description
            )
        }
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
