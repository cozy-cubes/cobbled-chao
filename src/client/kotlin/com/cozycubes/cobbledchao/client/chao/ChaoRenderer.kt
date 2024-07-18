/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.client.chao

import com.cozycubes.cobbledchao.chao.ChaoEntity
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.cache.`object`.GeoBone
import software.bernie.geckolib.renderer.specialty.DynamicGeoEntityRenderer


class ChaoRenderer(renderManager: EntityRendererProvider.Context?) :
    DynamicGeoEntityRenderer<ChaoEntity>(renderManager, ChaoModel()) {

    companion object {
        val WHITE_STAINED_GLASS_TEXTURE: ResourceLocation =
            ResourceLocation.withDefaultNamespace("textures/block/white_stained_glass.png")
    }

    override fun getTextureOverrideForBone(
        bone: GeoBone,
        animatable: ChaoEntity?,
        partialTick: Float
    ): ResourceLocation? {
        return if (bone.name == "outer_glass") WHITE_STAINED_GLASS_TEXTURE else null
    }
}