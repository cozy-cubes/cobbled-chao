/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
@file:Suppress("SameParameterValue")

package com.cozycubes.cobbledchao.chao

import com.cozycubes.cobbledchao.CobbledChao
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EntityType.EntityFactory
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.entity.PathfinderMob

object ChaoModule {
    val CHAO: EntityType<ChaoEntity> = register("chao", ::ChaoEntity, 1f, 1f)

    fun registerAll() {
        FabricDefaultAttributeRegistry.register(CHAO, ChaoEntity.ATTRIBUTES)
    }

    private fun <T : PathfinderMob> register(
        name: String,
        entity: EntityFactory<T>,
        width: Float,
        height: Float,
    ): EntityType<T> {
        return Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            CobbledChao.modResource(name),
            EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name)
        )
    }
}