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