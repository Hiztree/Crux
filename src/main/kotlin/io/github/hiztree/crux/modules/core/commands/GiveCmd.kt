package io.github.hiztree.crux.modules.core.commands

import io.github.hiztree.crux.api.command.BaseCommand
import io.github.hiztree.crux.api.command.annotations.*
import io.github.hiztree.crux.api.extensions.giveItem
import io.github.hiztree.crux.api.extensions.toText
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.GenericArguments
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.item.ItemType
import org.spongepowered.api.item.inventory.ItemStack

@Label(["give"])
@Description("Give a player a specify item.")
class GiveCmd : BaseCommand(arrayOf(GenericArguments.player("target".toText()), GenericArguments.catalogedElement("item".toText(), ItemType::class.java), GenericArguments.optional(GenericArguments.integer("qty".toText()), 64))) {

    fun performCmd(sender: CommandSource, args: Array<out String>, target: Player, type: ItemType, qty: Int) {
        val stack = ItemStack.of(type, qty)
        target.giveItem(stack)
    }
}

