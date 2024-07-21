package com.cozycubes.cobbledchao.client.chao

import com.cozycubes.cobbledchao.chao.ChaoEntity
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRendererProvider
import software.bernie.geckolib.cache.`object`.BakedGeoModel
import software.bernie.geckolib.cache.`object`.GeoBone
import software.bernie.geckolib.renderer.GeoRenderer
import software.bernie.geckolib.renderer.layer.GeoRenderLayer
import software.bernie.geckolib.renderer.specialty.DynamicGeoEntityRenderer


class BallLayer(entityRendererIn: GeoRenderer<ChaoEntity>) : GeoRenderLayer<ChaoEntity>(entityRendererIn) {
    private val model = BallModel()

    override fun renderForBone(
        poseStack: PoseStack,
        animatable: ChaoEntity,
        bone: GeoBone,
        renderType: RenderType,
        bufferSource: MultiBufferSource,
        buffer: VertexConsumer,
        partialTick: Float,
        packedLight: Int,
        packedOverlay: Int
    ) {
        if (bone.name != "ball_socket") {
            return
        }

        render(
            poseStack,
            animatable,
            model.getBakedModel(model.getModelResource(animatable)),
            model.getRenderType(animatable, BallModel.TEXTURE),
            bufferSource,
            buffer,
            partialTick,
            packedLight,
            packedOverlay
        )
    }
}

class ChaoRenderer(renderManager: EntityRendererProvider.Context) :
    DynamicGeoEntityRenderer<ChaoEntity>(renderManager, ChaoModel()) {

    init {
        addRenderLayer(BallLayer(this))
    }

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
