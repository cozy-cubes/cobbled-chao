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