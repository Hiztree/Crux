package io.github.hiztree.crux

import com.google.inject.Inject
import io.github.hiztree.crux.api.config.Config
import io.github.hiztree.crux.api.config.ConfigType
import org.spongepowered.api.config.ConfigDir
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameStartedServerEvent
import org.spongepowered.api.plugin.Plugin
import java.nio.file.Path
import java.util.logging.Logger

@Plugin(id = "crux", name = "Crux", version = "0.0.1", description = "A set of basic functions for Sponge servers.", authors = ["Hiztree"])
class Crux {

    @Inject
    @ConfigDir(sharedRoot = false)
    val dataFolder: Path? = null

    companion object {
        lateinit var instance: Crux
        lateinit var generalConfig: Config
        lateinit var langConfig: Config

        val log: Logger = Logger.getLogger("Crux")

        fun getConfig(type: ConfigType): Config {
            if (type == ConfigType.GENERAL)
                return generalConfig
            else if (type == ConfigType.LANGUAGE)
                return langConfig

            return generalConfig
        }
    }

    @Listener
    fun onServerStart(event: GameStartedServerEvent?) {
        instance = this

        /*generalConfig = Config("general.conf", dataFolder!!, false)
        langConfig = Config("language.conf", dataFolder!!, false)

        val modPackage: MutableList<ClassPath.ClassInfo> = ClassPath.from(Crux::class.java.classLoader)
                .getTopLevelClassesRecursive("io.github.hiztree.crux.modules").toMutableList()

        val modLoader = ModuleLoader()
        modLoader.load(modPackage)

        val tempModList = Lists.newArrayList(modPackage)

        for (classInfo in tempModList) {
            val split = classInfo.packageName.split(".")

            if (!ModuleLoader.moduleEnabled(split[split.lastIndex]) && !ModuleLoader.moduleEnabled(split[split.lastIndex - 1]) && !ModuleLoader.moduleEnabled(split[split.lastIndex - 2])) {
                modPackage.remove(classInfo)
            }
        }

        for (loadClasses in ClassPath.from(Crux::class.java.classLoader).getTopLevelClasses("io.github.hiztree.crux.loader")) {
            if (loadClasses.simpleName == "Loader")
                continue

            val loadedClass = loadClasses.load().newInstance()

            if (loadedClass is Loader && loadedClass !is ModuleLoader) {
                loadedClass.load(modPackage)
            }
        }*/
    }
}