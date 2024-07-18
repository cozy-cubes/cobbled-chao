/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.client.chao

import com.cozycubes.cobbledchao.chao.ChaoModule
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry

object ClientChaoModule {
    fun registerAll() {
        EntityRendererRegistry.register(ChaoModule.CHAO, ::ChaoRenderer)
    }
}