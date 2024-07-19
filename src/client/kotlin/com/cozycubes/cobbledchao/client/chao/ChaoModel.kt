/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.client.chao

import com.cozycubes.cobbledchao.CobbledChao.modResource
import com.cozycubes.cobbledchao.chao.ChaoEntity
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import software.bernie.geckolib.animation.AnimationState
import software.bernie.geckolib.cache.`object`.GeoBone
import software.bernie.geckolib.model.DefaultedEntityGeoModel

class ChaoModel : DefaultedEntityGeoModel<ChaoEntity>(MODEL) {
    companion object {
        val MODEL = modResource("chao/baby/chao_baby")
        val TEXTURE: ResourceLocation = modResource("textures/entity/chao/baby/chao_baby.png")
    }

    override fun getTextureResource(animatable: ChaoEntity): ResourceLocation {
        return TEXTURE
    }

    override fun setCustomAnimations(
        chaoEntity: ChaoEntity,
        instanceId: Long,
        animationState: AnimationState<ChaoEntity>
    ) {
        super.setCustomAnimations(chaoEntity, instanceId, animationState)

        val ballBone: GeoBone = animationProcessor.getBone("ball")
        if (ballBone != null) {
            val playerPos = Minecraft.getInstance().player?.position() ?: return
            val chaoPos = chaoEntity.position()
            val diffVec = playerPos.subtract(chaoPos)
            ballBone.rotY = Mth.wrapDegrees(Mth.atan2(diffVec.z, diffVec.x) * Mth.RAD_TO_DEG).toFloat() - 90f
        }
    }
}