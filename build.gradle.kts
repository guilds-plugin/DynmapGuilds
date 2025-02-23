import net.kyori.indra.IndraPlugin

plugins {
    java
    id("net.kyori.indra") version "3.1.3"
    id("de.eldoria.plugin-yml.bukkit") version "0.7.1"
}

group = "me.glaremasters"
version = "1.0-SNAPSHOT"

apply {
    plugin<IndraPlugin>()
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.mikeprimm.com/")
    maven("https://repo.glaremasters.me/repository/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.glaremasters:guilds:3.5.7.0")
    compileOnly("us.dynmap:DynmapCoreAPI:3.7-beta-6")
    compileOnly("us.dynmap:dynmap-api:3.7-beta-6")
}

bukkit {
    name = "DynmapGuilds"
    main = "me.glaremasters.dynmapguilds.DynmapGuilds"
    version = "1.0-SNAPSHOT"
    apiVersion = "1.21"
    authors = listOf("Glare")
    depend = listOf("Guilds", "dynmap")
}

tasks {
    indra {
        mitLicense()

        javaVersions {
            target(8)
        }

        github("guilds-plugin", "dynmapguilds") {
            publishing(true)
        }

        publishAllTo("guilds", "https://repo.glaremasters.me/repository/guilds/")
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    processResources {
        expand("version" to rootProject.version)
    }
}
