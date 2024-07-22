/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.chao

import com.cozycubes.cobbledchao.CobbledChao.logger
import com.cozycubes.cobbledchao.chaosdrive.ChaosDriveTags
import com.cozycubes.cobbledchao.data.NbtKeys
import com.cozycubes.cobbledchao.data.PremadeEntityAttributes.genericMovingAttribs
import com.cozycubes.cobbledchao.extensions.possibleCompound
import com.cozycubes.cobbledchao.network.Network
import com.cozycubes.cobbledchao.network.ParticlePayload
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtOps
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.util.GeckoLibUtil
import kotlin.math.cos
import kotlin.math.sin


class ChaoEntity(entityType: EntityType<out PathfinderMob>, level: Level) : PathfinderMob(entityType, level),
    GeoEntity {
    companion object {
        val ATTRIBUTES = genericMovingAttribs.build()

        // TODO: Probably config this and separate it out from the particle animation time?
        val EXP_COOLDOWN = 90
    }

    var chaoData: ChaoData = ChaoData()
    val isOnExpCooldown: Boolean
        get() = chaoData.expCooldown > 0

    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar?) {}

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache

    override fun load(chaoEntityNbt: CompoundTag) {
        super.load(chaoEntityNbt)
        chaoEntityNbt.possibleCompound(NbtKeys.CHAO.nbtKey)?.let {
            val loader = ChaoData.CODEC.parse(NbtOps.INSTANCE, it)
            if (loader.isSuccess) {
                chaoData = loader.orThrow
            } else {
                logger.info("Unable to load Chao's NBT")
            }
        }
    }

    override fun saveWithoutId(chaoEntityNbt: CompoundTag): CompoundTag {
        val saver = ChaoData.CODEC.encodeStart(NbtOps.INSTANCE, chaoData)
        if (saver.isSuccess) {
            chaoEntityNbt.put(NbtKeys.CHAO.nbtKey, saver.orThrow)
        } else {
            logger.info("Unable to save Chao's NBT")
        }
        return super.saveWithoutId(chaoEntityNbt)
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, FloatGoal(this))
        goalSelector.addGoal(
            2, TemptGoal(
                this, 1.0, { itemStack: ItemStack ->
                    itemStack.`is`(ChaosDriveTags.CHAOS_DRIVES.tag)
                }, false
            )
        )
        goalSelector.addGoal(3, WaterAvoidingRandomStrollGoal(this, 0.8))
        goalSelector.addGoal(
            4, LookAtPlayerGoal(
                this, Player::class.java, 8.0f
            )
        )
        goalSelector.addGoal(4, RandomLookAroundGoal(this))
        super.registerGoals()
    }

    override fun tick() {
        // TODO: Reset Chao's previous logic. Currently pauses mid-action, causing it to stutter.
        if (chaoData.expCooldown <= 0) {
            return super.tick()
        }

        val ticksTotal = 90
        val radius = 0.5
        val height = 0.45
        val rotationSpeed = 3.0

        val progressPercent = 1.0 * (ticksTotal - chaoData.expCooldown) / ticksTotal

        val angle = (2 * Math.PI) / ticksTotal * chaoData.expCooldown * rotationSpeed
        val particleX = x + radius * cos(angle) * (1 - progressPercent * 0.5)
        val particleZ = z + radius * sin(angle) * (1 - progressPercent * 0.5)
        val particleY = y + 0.2 + height * progressPercent

        spawnParticle(ParticleTypes.CRIT, particleX, particleY, particleZ, 0.0, 0.0, 0.0)

        if (chaoData.expCooldown == 1) {
            val explosionParticleCount = 6
            for (r in 0..explosionParticleCount) {
                val explosionAngle = (2 * Math.PI) / explosionParticleCount * r
                val explosionSpeedX = cos(explosionAngle)
                val explosionSpeedZ = sin(explosionAngle)
                spawnParticle(
                    ParticleTypes.CRIT, x, y + height, z, explosionSpeedX, 1.0, explosionSpeedZ
                )
            }
        }

        chaoData.expCooldown--
    }

    fun spawnParticle(
        particleType: SimpleParticleType,
        x: Double,
        y: Double,
        z: Double,
        speedX: Double,
        speedY: Double,
        speedZ: Double
    ) {
        level().players()
            .forEach {
                Network.sendAddParticlePacket(
                    it as ServerPlayer,
                    ParticlePayload(particleType, x, y, z, speedX, speedY, speedZ)
                )
            }
        // TODO[RESEARCH]: Why does this sometimes not send to someone standing right next to the Chao?
//        level().getNearbyPlayers(
//            TargetingConditions.DEFAULT.selector { it is ServerPlayer },
//            this,
//            AABB.ofSize(position(), 64.0, 64.0, 64.0)
//        ).forEach {
//            Network.sendAddParticlePacket(
//                it as ServerPlayer,
//                ParticlePayload(particleType, x, y, z, speedX, speedY, speedZ)
//            )
//        }
    }

    fun usedChaoDrive() {
        chaoData.expCooldown = EXP_COOLDOWN
    }
}
