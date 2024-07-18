/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.util

import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes

object PremadeEntityAttributes {
    var genericMovingAttribs: AttributeSupplier.Builder = Mob.createMobAttributes()
        .add(Attributes.FOLLOW_RANGE, 16.0)
        .add(Attributes.MAX_HEALTH, 1.0)
        .add(Attributes.MOVEMENT_SPEED, 0.25)
}