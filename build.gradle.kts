plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "9.4.3"
}

group = "cn.dreeam.surf"
version = "5.0.0"
description = "Policy-driven item integrity validation plugin for Paper servers."

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

    // FoliaLib
    maven {
        name = "tcoded-releases"
        url = uri("https://repo.tcoded.com/releases")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    implementation("com.github.thatsmusic99:ConfigurationMaster-API:v2.0.0-rc.3") {
        exclude(group = "org.yaml", module = "snakeyaml")
    }
    implementation("org.bstats:bstats-bukkit:3.2.1")
    implementation("com.tcoded:FoliaLib:0.5.2")
    implementation("com.github.cryptomorin:XSeries:13.7.1")
    implementation("de.tr7zw:item-nbt-api:2.15.8") // TODO - evaluate with rtag
    compileOnly(files("libs/RoseStacker-1.5.41.jar"))
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.${archiveExtension.get()}")
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
                mapOf(
                    "name" to project.name,
                    "version" to project.version,
                    "description" to description
                )
            )
        }
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
