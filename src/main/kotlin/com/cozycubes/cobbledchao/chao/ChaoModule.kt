/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
@file:Suppress("SameParameterValue")

package com.cozycubes.cobbledchao.chao

import com.cozycubes.cobbledchao.base.AbstractModule
import com.cozycubes.cobbledchao.base.BaseRegistry.registerEntityType
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.world.entity.EntityType

object ChaoModule : AbstractModule() {
    val CHAO: EntityType<ChaoEntity> = registerEntityType("chao", ::ChaoEntity, 1f, 1f)

    override fun init() {
        FabricDefaultAttributeRegistry.register(CHAO, ChaoEntity.ATTRIBUTES)
    }
}