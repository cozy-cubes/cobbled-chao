package com.cozycubes.cobbledchao.trees

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property
import kotlin.math.min

class SaplingBlock(properties: Properties) : Block(properties) {
    companion object {
        val MAX_AGE = 6
        val MAX_SIZE = 2
        val AGE = IntegerProperty.create("age", 0, MAX_AGE)
        val SIZE = IntegerProperty.create("size", 0, MAX_SIZE)
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(AGE, 0).setValue(SIZE, 0))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(*arrayOf<Property<*>>(AGE, SIZE))
    }

    override fun randomTick(
        blockState: BlockState, serverLevel: ServerLevel, blockPos: BlockPos, randomSource: RandomSource
    ) {
        val age = blockState.getValue(AGE)
        val nextAge = age + 1
        serverLevel.setBlockAndUpdate(blockPos.above(nextAge), TreeModule.CHAO_TREE_TRUNK.defaultBlockState())
        serverLevel.setBlockAndUpdate(
            blockPos, defaultBlockState().setValue(AGE, nextAge).setValue(SIZE, min(nextAge, MAX_SIZE))
        )
        for (i in 1..min(MAX_SIZE, nextAge - 1)) {
            serverLevel.setBlockAndUpdate(
                blockPos.above(i),
                TreeModule.CHAO_TREE_TRUNK.defaultBlockState().setValue(TrunkBlock.SIZE, min(nextAge, MAX_SIZE) - i)
            )
        }
    }

    override fun isRandomlyTicking(blockState: BlockState): Boolean = blockState.getValue(AGE) < MAX_AGE
}