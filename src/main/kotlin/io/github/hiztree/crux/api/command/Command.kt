/*
 * Copyright (C) Nexuma, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Levi Pawlak <levi.pawlak17@gmail.com>, September 2020
 */

package io.github.hiztree.crux.api.command

import com.google.common.collect.Lists
import io.github.hiztree.crux.api.extensions.sendPrefixMsg
import io.github.hiztree.crux.modules.lang.LangCommands
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player
import java.lang.reflect.Method

open class Command(private val instance: BaseCommand, private val playerOnly: Boolean, private val method: Method) : CommandExecutor {

    override fun execute(src: CommandSource, args: CommandContext): CommandResult {
        if (playerOnly && src !is Player) {
            src.sendPrefixMsg(LangCommands.Error.playerSender)
            return CommandResult.empty()
        }

        val parsedArgs = Lists.newArrayList<Any>()

        for (element in instance.elements) {
            parsedArgs.add(args.getOne<Any>(element.key!!).orElse(null))
        }

        method.invoke(instance, *parsedArgs.toTypedArray())

        return CommandResult.empty()
    }
}



