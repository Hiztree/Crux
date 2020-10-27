/*
 * Copyright (C) Nexuma, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Levi Pawlak <levi.pawlak17@gmail.com>, September 2020
 */

package io.github.hiztree.crux.api.module

abstract class Module {

    var loaded: Boolean = false
    var enabled: Boolean = true

    open fun onEnable() {}
    open fun onDisable() {}
}