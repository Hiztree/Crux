package io.github.hiztree.crux.modules.lang

import io.github.hiztree.crux.api.config.ConfigSection
import io.github.hiztree.crux.api.config.ConfigType
import io.github.hiztree.crux.api.config.annotations.ConfigField
import io.github.hiztree.crux.api.config.annotations.ConfigSpec

@ConfigSpec(["lang", "general"], ConfigType.LANGUAGE)
object LangGeneral : ConfigSection {

    @ConfigField
    var prefix: String = "&8[&aCrux&8]&r "
}

@ConfigSpec(["lang", "commands"], ConfigType.LANGUAGE)
object LangCommands : ConfigSection {

    @ConfigSpec(["lang", "commands", "error"], ConfigType.LANGUAGE)
    object Error {
        @ConfigField
        var playerSender: String = "&cYou must be a player to perform this command."

        @ConfigField
        var permission: String = "&cYou do not have enough permission to perform this command."

        @ConfigField
        var usage: String = "&cUsage: &7{0}."

        @ConfigField
        var arg: String = "&cCould not get the argument for: &7{0}!"

        @ConfigField
        var general: String = "&cCould not perform the command!"

        @ConfigField
        var emptyHand: String = "&cYou must be holding an item!"
    }

    @ConfigSpec(["lang", "commands", "heal"], ConfigType.LANGUAGE)
    object Heal {
        @ConfigField
        var self: String = "&7You have healed yourself."

        @ConfigField
        var other: String = "&7You have healed &c{0}&7."
    }

    @ConfigSpec(["lang", "commands", "repair"], ConfigType.LANGUAGE)
    object Repair {
        @ConfigField
        var hand: String = "&7You have repaired the item in your hand."

        @ConfigField
        var all: String = "&7You have repaired all the items in your inventory."
    }

    @ConfigSpec(["lang", "commands", "weather"], ConfigType.LANGUAGE)
    object Weather {
        @ConfigField
        var change: String = "&7You have changed the weather to {0}."
    }


}


