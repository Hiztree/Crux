/*
 * Copyright (C) Nexuma, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Levi Pawlak <levi.pawlak17@gmail.com>, September 2020
 */

package io.github.hiztree.crux.api.module

/**
 * The module specification.
 *
 * @property id the id of the module
 * @property required if the module is required
 * @property depend if the module depends on another (will load depends first)
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ModuleSpec(
        val id: String,
        val required: Boolean = true,
        val depend: Array<String> = [""]
)
