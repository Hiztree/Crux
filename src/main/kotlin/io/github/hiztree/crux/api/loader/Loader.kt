/*
 * Copyright (C) Nexuma, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Levi Pawlak <levi.pawlak17@gmail.com>, September 2020
 */

package io.github.hiztree.crux.api.loader

import com.google.common.reflect.ClassPath

abstract class Loader {

    //Empty constructor needed.
    constructor()

    abstract fun load(set: List<ClassPath.ClassInfo>): Boolean
}