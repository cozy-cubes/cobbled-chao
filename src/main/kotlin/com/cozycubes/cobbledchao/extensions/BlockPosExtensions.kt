package com.cozycubes.cobbledchao.extensions

import net.minecraft.core.BlockPos

fun BlockPos.flip(flip: Boolean): BlockPos = if (flip) BlockPos(0 - this.x, this.y, 0 - this.z) else this