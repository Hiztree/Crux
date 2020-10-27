/*
 * Copyright (C) Nexuma, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Levi Pawlak <levi.pawlak17@gmail.com>, September 2020
 */

package io.github.hiztree.crux.api.config.annotations

import io.github.hiztree.crux.api.config.ConfigType

/**
 * The config specification.
 *
 * @property id the ID for the config section
 * @property config the type of config
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ConfigSpec(val key: Array<out String>, val config: ConfigType)