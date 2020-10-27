package io.github.hiztree.crux.modules.core

import com.google.common.collect.Lists
import io.github.hiztree.crux.api.config.ConfigSection
import io.github.hiztree.crux.api.config.ConfigType
import io.github.hiztree.crux.api.config.annotations.ConfigField
import io.github.hiztree.crux.api.config.annotations.ConfigSpec

@ConfigSpec(["core"], ConfigType.GENERAL)
object CoreConfig : ConfigSection {

    @ConfigField("True if you want to allow enchants to exceed their natural maximum levels.")
    var allowUnsafeEnchants: Boolean = false

    @ConfigField("The list of disabled modules. Empty if no modules are to be disabled.")
    var disabledModules: List<String> = Lists.newArrayList()
}