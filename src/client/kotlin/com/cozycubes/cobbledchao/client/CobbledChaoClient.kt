/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.client

import com.cozycubes.cobbledchao.client.chao.ClientChaoModule
import com.cozycubes.cobbledchao.client.network.ClientNetwork
import com.cozycubes.cobbledchao.trees.TreeModule
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.renderer.RenderType

object CobbledChaoClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientChaoModule.registerAll()
        ClientNetwork.init()
        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderType.cutout(),
            TreeModule.CHAO_TREE_SEED
        )
    }
}