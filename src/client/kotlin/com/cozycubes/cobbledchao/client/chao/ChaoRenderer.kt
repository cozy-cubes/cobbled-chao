/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.client.chao

import com.cozycubes.cobbledchao.chao.ChaoEntity
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRendererProvider
import software.bernie.geckolib.cache.`object`.BakedGeoModel
import software.bernie.geckolib.renderer.specialty.DynamicGeoEntityRenderer


class ChaoRenderer(renderManager: EntityRendererProvider.Context) :
    DynamicGeoEntityRenderer<ChaoEntity>(renderManager, ChaoModel()) {
    override fun preRender(
        poseStack: PoseStack,
        animatable: ChaoEntity,
        model: BakedGeoModel,
        bufferSource: MultiBufferSource?,
        buffer: VertexConsumer?,
        isReRender: Boolean,
        partialTick: Float,
        packedLight: Int,
        packedOverlay: Int,
        colour: Int
    ) {
        if (!isReRender) {
            poseStack.scale(0.5f, 0.5f, 0.5f)
        }
        super.preRender(
            poseStack,
            animatable,
            model,
            bufferSource,
            buffer,
            isReRender,
            partialTick,
            packedLight,
            packedOverlay,
            colour
        )
    }
}