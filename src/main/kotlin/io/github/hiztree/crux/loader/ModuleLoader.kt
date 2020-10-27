/*
 * Copyright (C) Nexuma, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Levi Pawlak <levi.pawlak17@gmail.com>, September 2020
 */

package io.github.hiztree.crux.loader

import com.google.common.collect.Maps
import com.google.common.reflect.ClassPath
import io.github.hiztree.crux.Crux
import io.github.hiztree.crux.api.loader.Loader
import io.github.hiztree.crux.api.module.Module
import io.github.hiztree.crux.api.module.ModuleSpec
import io.github.hiztree.crux.modules.core.CoreConfig
import java.util.*

class ModuleLoader : Loader() {

    companion object {
        private val modules: HashMap<ModuleSpec, Module> = Maps.newHashMap()

        fun moduleEnabled(id: String): Boolean {
            val mod = getModule(id) ?: return false

            return mod.value.enabled
        }

        private fun getModule(id: String): Map.Entry<ModuleSpec, Module>? {
            for (entry in modules.entries) {
                if (entry.key.id.equals(id, true))
                    return entry
            }

            return null
        }
    }

    override fun load(set: List<ClassPath.ClassInfo>): Boolean {
        for (info in set.stream().filter { info: ClassPath.ClassInfo ->
            info.load().isAnnotationPresent(ModuleSpec::class.java)
        }) {
            try {
                val loaded = info.load()
                val spec = loaded.getAnnotation(ModuleSpec::class.java)
                val mod = loaded.newInstance() as Module

                modules[spec] = mod
            } catch (ex: ReflectiveOperationException) {
                Crux.log.severe("Could not load modules!")

                return false
            }
        }

        for ((spec, mod) in modules) {
            loadModule(spec, mod)
        }

        return true
    }

    private fun loadModule(spec: ModuleSpec, mod: Module) {
        if (!mod.loaded) {
            for (depend in spec.depend) {
                val dependMod = getModule(depend)

                if (dependMod != null) {
                    loadModule(dependMod.key, dependMod.value)
                }
            }

            mod.enabled = !CoreConfig.disabledModules.contains(spec.id.toLowerCase())

            if (mod.enabled || spec.required) {
                mod.onEnable()
                Crux.log.info("The module ${spec.id} has been enabled!")
            } else {
                Crux.log.info("The module ${spec.id} was not loaded as its disabled!")
            }

            mod.loaded = true
        }
    }


}