/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.client.chao

import com.cozycubes.cobbledchao.CobbledChao.MOD_ID
import com.cozycubes.cobbledchao.chao.ChaoEntity
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.model.DefaultedEntityGeoModel

class ChaoModel : DefaultedEntityGeoModel<ChaoEntity>(RESOURCE_LOCATION) {
    companion object {
        val RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(MOD_ID, "chao")
        val REDSTONE_BLOCK_TEXTURE: ResourceLocation = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/redstone_block.png")
    }

    override fun getTextureResource(animatable: ChaoEntity): ResourceLocation {
        return REDSTONE_BLOCK_TEXTURE
    }

    override fun getRenderType(animatable: ChaoEntity, texture: ResourceLocation): RenderType {
        return RenderType.entityTranslucent(texture)
    }
}