package io.github.hiztree.crux.api.command

import org.spongepowered.api.command.args.CommandElement

/**
 * Just a dummy base to check if a class is a command.
 */
abstract class BaseCommand(val elements: Array<out CommandElement> = emptyArray())

/**
 * Just a dummy base to check if a class is a command.
 */
abstract class SubCommand(val label: String, elements: Array<out CommandElement> = emptyArray()) : BaseCommand(elements)