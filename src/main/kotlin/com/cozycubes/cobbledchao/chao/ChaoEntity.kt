/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.chao

import com.cozycubes.cobbledchao.chaosdrive.ChaosDriveTags
import com.cozycubes.cobbledchao.data.NbtKeys
import com.cozycubes.cobbledchao.extensions.possibleCompound
import com.cozycubes.cobbledchao.util.PremadeEntityAttributes.genericMovingAttribs
import net.minecraft.nbt.CompoundTag
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


class ChaoEntity(entityType: EntityType<out PathfinderMob>, level: Level) : PathfinderMob(entityType, level),
    GeoEntity {
    companion object {
        val ATTRIBUTES = genericMovingAttribs.build()
    }

    var chaoData: ChaoData = ChaoData.generateZeroes()

    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar?) {}

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache

    override fun load(chaoEntityNbt: CompoundTag) {
        super.load(chaoEntityNbt)
        if (chaoEntityNbt.contains(NbtKeys.CHAO.nbtKey)) {
            chaoData = ChaoData.fromNbt(chaoEntityNbt.possibleCompound(NbtKeys.CHAO.nbtKey))
        }
    }

    override fun saveWithoutId(chaoEntityNbt: CompoundTag): CompoundTag {
        val chaoDataNbt = chaoData.toNbt()
        chaoEntityNbt.put(NbtKeys.CHAO.nbtKey, chaoDataNbt)
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
}
