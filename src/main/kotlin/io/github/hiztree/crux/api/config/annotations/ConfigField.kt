/*
 * Copyright (C) Nexuma, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Levi Pawlak <levi.pawlak17@gmail.com>, September 2020
 */

package io.github.hiztree.crux.api.config.annotations

/**
 * Creates a new config field. Used to load class parameters as config values.
 *
 * @property comment the comment for the config value
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ConfigField(val comment: String = "")