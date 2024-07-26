/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.chaosdrive

import com.cozycubes.cobbledchao.base.AbstractModule
import com.cozycubes.cobbledchao.base.BaseRegistry.registerItem
import com.cozycubes.cobbledchao.chao.ChaoStat

object ChaosDriveModule : AbstractModule() {
    // TODO: Make datapackable.
    val YELLOW_DRIVE = registerItem(
        ChaosDrive(
            mapOf(
                ChaoStat.Companion.STATS.SWIM to 24
            )
        ), "yellow_drive"
    )
    val PURPLE_DRIVE = registerItem(
        ChaosDrive(
            mapOf(
                ChaoStat.Companion.STATS.FLY to 24
            )
        ), "purple_drive"
    )
    val GREEN_DRIVE = registerItem(
        ChaosDrive(
            mapOf(
                ChaoStat.Companion.STATS.RUN to 24
            )
        ), "green_drive"
    )
    val RED_DRIVE = registerItem(
        ChaosDrive(
            mapOf(
                ChaoStat.Companion.STATS.POWER to 24
            )
        ), "red_drive"
    )

    override fun init() {}
}