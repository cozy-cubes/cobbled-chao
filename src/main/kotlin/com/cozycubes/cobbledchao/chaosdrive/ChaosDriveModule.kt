/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.chaosdrive

import com.cozycubes.cobbledchao.CobbledChao.modResource
import com.cozycubes.cobbledchao.chao.ChaoStat
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries

object ChaosDriveModule {
    // TODO: Make datapackable.
    val YELLOW_DRIVE = ChaosDrive(
        mapOf(
            ChaoStat.Companion.STATS.SWIM to 24
        )
    )
    val PURPLE_DRIVE = ChaosDrive(
        mapOf(
            ChaoStat.Companion.STATS.FLY to 24
        )
    )
    val GREEN_DRIVE = ChaosDrive(
        mapOf(
            ChaoStat.Companion.STATS.RUN to 24
        )
    )
    val RED_DRIVE = ChaosDrive(
        mapOf(
            ChaoStat.Companion.STATS.POWER to 24
        )
    )

    fun registerAll() {
        Registry.register(BuiltInRegistries.ITEM, modResource("green_drive"), GREEN_DRIVE)
        Registry.register(BuiltInRegistries.ITEM, modResource("red_drive"), RED_DRIVE)
        Registry.register(BuiltInRegistries.ITEM, modResource("purple_drive"), PURPLE_DRIVE)
        Registry.register(BuiltInRegistries.ITEM, modResource("yellow_drive"), YELLOW_DRIVE)
    }
}