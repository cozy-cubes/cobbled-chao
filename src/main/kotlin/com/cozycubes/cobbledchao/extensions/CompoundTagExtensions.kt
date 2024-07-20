/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.extensions

import net.minecraft.nbt.CompoundTag

fun CompoundTag.possibleCompound(key: String, fallback: CompoundTag? = null): CompoundTag? {
    return if (this.contains(key)) this.getCompound(key) else fallback
}