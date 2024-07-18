/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.chaosdrive

import com.cozycubes.cobbledchao.CobbledChao.modResource
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

enum class ChaosDriveTags(val tag: TagKey<Item>) {
    CHAOS_DRIVES(TagKey.create(Registries.ITEM, modResource("chaos_drives")))
}