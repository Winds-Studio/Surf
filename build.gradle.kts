plugins {
    `java-library`
    `maven-publish`
    id("io.github.goooler.shadow") version "8.1.7" apply true
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
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }

    // FoliaLib
    maven {
        name = "devmart-other"
        url = uri("https://nexuslite.gcnt.net/repos/other/")
    }
}

val adventureVersion = "4.16.0"

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("org.apache.logging.log4j:log4j-api:2.23.1")
    implementation("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:1.3.0-M2")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("com.tcoded:FoliaLib:0.3.1")
    implementation("com.github.cryptomorin:XSeries:11.1.0")
    compileOnly("de.tr7zw:item-nbt-api-plugin:2.13.1")
    compileOnly("dev.rosewood.rosestacker:RoseStacker:1.5.20")

    implementation("net.kyori:adventure-platform-bukkit:4.3.2")
    implementation("net.kyori:adventure-api:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-legacy:$adventureVersion")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.build.configure {
    dependsOn("shadowJar")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName = "${project.name}-${project.version}.${archiveExtension.get()}"
    exclude("META-INF/**") // Dreeam - Avoid to include META-INF/maven in Jar
    relocate("net.kyori", "cn.dreeam.surf.libs.kyori")
    relocate("org.yaml.snakeyaml", "cn.dreeam.surf.libs.snakeyaml")
    relocate("space.arim.dazzleconf", "cn.dreeam.surf.libs.dazzleconf")
    relocate("org.bstats", "cn.dreeam.surf.libs.bstats")
    relocate("com.tcoded.folialib", "cn.dreeam.surf.libs.folialib")
    relocate("com.cryptomorin.xseries", "cn.dreeam.surf.libs.xseries")
}

tasks {
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
