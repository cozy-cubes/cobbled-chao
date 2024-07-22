/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao

import com.cozycubes.cobbledchao.chao.ChaoModule
import com.cozycubes.cobbledchao.chaosdrive.ChaosDriveModule
import com.cozycubes.cobbledchao.trees.TreeModule
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory

object CobbledChao : ModInitializer {
    val MOD_ID = "cobbled-chao"
    val logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        logger.info("cobbled-chao loading")
        ChaoModule.registerAll()
        ChaosDriveModule.registerAll()
        TreeModule.registerAll()
    }

    fun modResource(name: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name)
    }
}