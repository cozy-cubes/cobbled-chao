package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.trees.Properties.MARKED
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.Property

class LeafBlock(properties: Properties) : Block(properties), TreePart {
    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(MARKED)
    }

    override fun tick(
        blockState: BlockState,
        serverLevel: ServerLevel,
        blockPos: BlockPos,
        randomSource: RandomSource
    ) {
        if (blockState.getValue(MARKED)) {
            serverLevel.destroyBlock(blockPos, true)
            cascadeBreakage(serverLevel, blockPos)
        }
    }

    override fun destroy(levelAccessor: LevelAccessor, blockPos: BlockPos, blockState: BlockState) {
        cascadeBreakage(levelAccessor, blockPos)
    }
}