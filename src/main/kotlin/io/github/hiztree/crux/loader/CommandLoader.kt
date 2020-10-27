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
import io.github.hiztree.crux.Crux
import io.github.hiztree.crux.api.command.BaseCommand
import io.github.hiztree.crux.api.command.Command
import io.github.hiztree.crux.api.command.SubCommand
import io.github.hiztree.crux.api.command.annotations.Description
import io.github.hiztree.crux.api.command.annotations.Label
import io.github.hiztree.crux.api.command.annotations.Permission
import io.github.hiztree.crux.api.command.annotations.PlayerOnly
import io.github.hiztree.crux.api.extensions.toText
import io.github.hiztree.crux.api.loader.Loader
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.spec.CommandSpec


class CommandLoader : Loader() {

    override fun load(set: List<ClassPath.ClassInfo>): Boolean {
        for (info in set.stream().filter { info: ClassPath.ClassInfo ->
            val loaded = info.load()
            loaded.interfaces.contains(BaseCommand::class.java) && loaded.isAnnotationPresent(Label::class.java)
        }) {
            try {
                val loaded = info.load()
                val baseCmd = loaded.newInstance() as BaseCommand
                val labels = loaded.getAnnotation(Label::class.java).labels
                val description = loaded.getAnnotation(Description::class.java).description
                val permission = loaded.getAnnotation(Permission::class.java).permission
                val executor = loadCommand(loaded, baseCmd) ?: return false
                val children = Maps.newHashMap<CommandSpec, String>()

                for (declaredClass in loaded.declaredClasses) {
                    val subCmd = declaredClass.newInstance() as SubCommand
                    val subDesc = declaredClass.getAnnotation(Description::class.java).description
                    val subPermission = declaredClass.getAnnotation(Permission::class.java).permission
                    val subExecutor = loadCommand(declaredClass, subCmd) ?: return false

                    val subSpec = CommandSpec.builder()
                            .description(subDesc.toText())
                            .permission(subPermission)
                            .arguments(*subCmd.elements)
                            .executor(subExecutor)
                            .build()

                    children[subSpec] = subCmd.label
                }

                val cmdSpec = CommandSpec.builder()
                        .description(description.toText())
                        .permission(permission)
                        .arguments(*baseCmd.elements)
                        .executor(executor)

                for ((spec, label) in children.entries) {
                    cmdSpec.child(spec, label)
                }

                Sponge.getCommandManager().register(Crux.instance, cmdSpec.build(), *labels)
            } catch (ex: ReflectiveOperationException) {
                Crux.log.severe("Could not load command: ${info.name}!")

                return false
            }
        }

        return true
    }

    private fun loadCommand(loaded: Class<*>, cmd: BaseCommand): Command? {
        var executor: Command? = null

        parent@ for (m in loaded.declaredMethods) {
            if(m.parameterCount != cmd.elements.size)
                continue@parent

            executor = Command(cmd, loaded.isAnnotationPresent(PlayerOnly::class.java), m)
        }

        if(executor == null) {
            Crux.log.severe("Could not load command: ${loaded.simpleName}. Did not specify the args correctly.")
        }

        return executor
    }
}