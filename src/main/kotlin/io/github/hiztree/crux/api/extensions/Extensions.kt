package io.github.hiztree.crux.api.extensions

import io.github.hiztree.crux.modules.lang.LangGeneral
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.data.key.Keys
import org.spongepowered.api.entity.EntityTypes
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.item.inventory.ItemStack
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.serializer.TextSerializers


fun CommandSource.sendPrefixMsg(msg: String, vararg args: Any) {
    var replace = msg

    for (i in args.indices) {
        replace = replace.replace("{$i}", args[i].toString())
    }

    this.sendMessage(LangGeneral.prefix.translate() + replace.translate())
}

operator fun Text.plus(translate: Text): Text {
    return Text.join(this, translate)
}

fun String.translate(): Text {
    return TextSerializers.FORMATTING_CODE.deserialize(this)
}

fun Player.giveItem(stack: ItemStack) {
    for (rejectedItem in this.inventory.offer(stack).rejectedItems) {
        val item = this.location.extent.createEntity(EntityTypes.ITEM, this.location.position)
        item.offer(Keys.REPRESENTED_ITEM, rejectedItem)

        this.location.extent.spawnEntity(item)
    }
}

fun String.toText(): Text {
    return Text.of(this)
}
