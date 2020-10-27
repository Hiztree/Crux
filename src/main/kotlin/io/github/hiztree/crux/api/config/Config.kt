/*
 * Copyright (C) Nexuma, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Levi Pawlak <levi.pawlak17@gmail.com>, September 2020
 */

package io.github.hiztree.crux.api.config

import com.google.common.collect.Lists
import com.google.common.reflect.TypeToken
import io.github.hiztree.crux.Crux
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.commented.CommentedConfigurationNode
import ninja.leaping.configurate.hocon.HoconConfigurationLoader
import org.spongepowered.api.Sponge
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Creates a new config base off the name and directory.
 */
open class Config(name: String, dir: Path, hasAsset: Boolean) {

    private val file: Path = dir.relativize(Paths.get("/$name"))
    private var loader: HoconConfigurationLoader
    private var rootNode: CommentedConfigurationNode

    init {
        if (!Files.exists(file)) {
            Files.createFile(file)

            if (hasAsset) {
                Sponge.getAssetManager().getAsset(Crux.instance, "general.conf").get().copyToFile(file, true, true)
            }
        }

        loader = HoconConfigurationLoader.builder().setPath(file).build()
        rootNode = loader.load()
    }

    /**
     * Gets the root node of the config.
     */
    open fun getRoot(): CommentedConfigurationNode {
        return rootNode
    }

    /**
     * Saves the config.
     */
    open fun save() {
        loader.save(rootNode)
    }
}

/**
 * Gets a list of strings.
 */
fun ConfigurationNode.getStringList(): List<String> {
    return this.getList(TypeToken.of(String::class.java), Lists.newArrayList())
}
