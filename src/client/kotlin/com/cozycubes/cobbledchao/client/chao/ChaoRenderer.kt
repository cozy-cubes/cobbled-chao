package com.cozycubes.cobbledchao.client.chao

import com.cozycubes.cobbledchao.chao.ChaoEntity
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.world.phys.Vec3
import org.joml.Vector3d
import software.bernie.geckolib.cache.`object`.BakedGeoModel
import software.bernie.geckolib.renderer.specialty.DynamicGeoEntityRenderer
import kotlin.math.atan2
import kotlin.math.sqrt

class ChaoRenderer(renderManager: EntityRendererProvider.Context) :
    DynamicGeoEntityRenderer<ChaoEntity>(renderManager, ChaoModel()) {
    override fun preRender(
        poseStack: PoseStack?,
        animatable: ChaoEntity?,
        model: BakedGeoModel?,
        bufferSource: MultiBufferSource?,
        buffer: VertexConsumer?,
        isReRender: Boolean,
        partialTick: Float,
        packedLight: Int,
        packedOverlay: Int,
        colour: Int
    ) {
        if (model == null || animatable == null) return

        val player = Minecraft.getInstance().player ?: return
        val worldPosition = animatable.position()

        model.getBone("ball").ifPresent { ballBone ->
            val rotation = getRotationVectorTowardsPlayer(worldPosition, player.position())
            val rootBone = model.topLevelBones[0]
            ballBone.updateRotation(
                rotation.x.toFloat() - rootBone.rotX,
                rotation.y.toFloat() - rootBone.rotY,
                rotation.z.toFloat() - rootBone.rotZ
            )
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

    private fun getRotationVectorTowardsPlayer(bonePos: Vec3, playerPos: Vec3): Vector3d {
        val direction = playerPos.subtract(bonePos).normalize()

        val pitch = atan2(direction.y, sqrt(direction.x * direction.x + direction.z * direction.z)) * (180 / Math.PI)
        val yaw = atan2(direction.x, direction.z) * (180 / Math.PI)

        return Vector3d((pitch), (yaw), 0.0)
    }
}
