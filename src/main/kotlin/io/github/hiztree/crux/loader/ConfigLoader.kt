/*
 * Copyright (C) Nexuma, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Levi Pawlak <levi.pawlak17@gmail.com>, September 2020
 */

package io.github.hiztree.crux.loader

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.google.common.reflect.ClassPath
import com.google.common.reflect.TypeToken
import io.github.hiztree.crux.Crux
import io.github.hiztree.crux.api.config.ConfigSection
import io.github.hiztree.crux.api.config.annotations.ConfigField
import io.github.hiztree.crux.api.config.annotations.ConfigSpec
import io.github.hiztree.crux.api.loader.Loader
import org.apache.commons.lang3.StringUtils
import kotlin.reflect.full.createInstance

class ConfigLoader : Loader() {
    override fun load(set: List<ClassPath.ClassInfo>): Boolean {
        for (info in set.stream().filter { info: ClassPath.ClassInfo ->
            ConfigSection::class.java.isAssignableFrom(info.load())
        }) {
            try {
                val classLoaded = info.load()

                val loadList = Lists.newArrayList<Class<*>>()
                loadList.add(classLoaded)
                loadList.addAll(classLoaded.declaredClasses)

                for (clazz in loadList) {
                    val spec = clazz.getAnnotation(ConfigSpec::class.java) ?: continue
                    val instance = clazz.kotlin.objectInstance ?: clazz.kotlin.createInstance()

                    val values = Maps.newHashMap<String, Pair<Any, String>>()
                    val config = Crux.getConfig(spec.config)

                    var changed = false

                    for (field in clazz.declaredFields) {
                        val configField = field.getAnnotation(ConfigField::class.java)
                        val key = arrayOf(*spec.key, field.name)

                        if (configField != null) {
                            field.isAccessible = true

                            values[field.name] = Pair(field.get(instance), configField.comment)

                            val node = config.getRoot().getNode(*key)

                            if (!node.isVirtual) {
                                field.set(instance, node.getValue(TypeToken.of(field.genericType)))
                            } else {
                                node.value = field.get(instance)

                                if (StringUtils.isNotEmpty(configField.comment))
                                    node.setComment(configField.comment)

                                changed = true
                            }
                        }
                    }

                    if (changed)
                        config.save()

                    if (config.getRoot().getNode(*spec.key).isVirtual) {
                        for ((k, v) in values) {
                            config.getRoot().getNode(*spec.key, k).value = v.first

                            if (v.second.isNotEmpty()) {
                                config.getRoot().getNode(*spec.key, k).setComment(v.second)
                            }
                        }

                        config.save()
                    }
                }
            } catch (ignore: ReflectiveOperationException) {
                Crux.log.severe("Could not load config: ${info.name}!")
                return false
            }
        }

        return true
    }

}