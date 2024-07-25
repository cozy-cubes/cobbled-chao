package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.trees.Properties.MARKED
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelAccessor

interface TreePart {
    fun cascadeBreakage(levelAccessor: LevelAccessor, blockPos: BlockPos) {
        for (x in -1..1) {
            for (y in -1..1) {
                for (z in -1..1) {
                    if (x == 0 && y == 0 && z == 0) continue

                    val targetPos = blockPos.offset(x, y, z)
                    val target = levelAccessor.getBlockState(targetPos)
                    if (
                        target.block is TreePart
                    ) {
                        levelAccessor.setBlock(targetPos, target.setValue(MARKED, true), 2)
                        levelAccessor.scheduleTick(targetPos, target.block, 1)
                    }
                }
            }
        }
    }
}