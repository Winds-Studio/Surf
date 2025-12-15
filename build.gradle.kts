plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "9.3.0"
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
        url = uri("https://repo.bsdevelopment.org/releases/")
    }

    // JitPack
    maven {
        name = "jitpack.io"
        url = uri("https://jitpack.io/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    implementation("com.github.thatsmusic99:ConfigurationMaster-API:v2.0.0-rc.3") {
        exclude(group = "org.yaml", module = "snakeyaml")
    }
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("com.github.technicallycoded:FoliaLib:0.4.4")
    implementation("com.github.cryptomorin:XSeries:v13.5.1")
    implementation("de.tr7zw:item-nbt-api:2.15.3")
    compileOnly(files("libs/RoseStacker-1.5.22.jar"))
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
        relocate("io.github.thatsmusic99.configurationmaster", "${project.group}.libs.configurationmaster")
        relocate("org.bstats", "${project.group}.libs.bstats")
        relocate("com.tcoded.folialib", "${project.group}.libs.folialib")
        relocate("com.cryptomorin.xseries", "${project.group}.libs.xseries")
        relocate("de.tr7zw.changeme.nbtapi", "${project.group}.libs.nbtapi")
    }

    processResources {
        filesMatching("**/plugin.yml") {
            expand(
                "name" to project.name,
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
