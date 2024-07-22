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
        if (nextAge - 1 <= MAX_SIZE) {
            for (i in 1..<nextAge) {
                serverLevel.setBlockAndUpdate(
                    blockPos.above(i),
                    TreeModule.CHAO_TREE_TRUNK.defaultBlockState().setValue(TrunkBlock.SIZE, min(nextAge, MAX_SIZE) - i)
                )
            }
        }
        if (nextAge == MAX_AGE) {
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).north(), TreeModule.LEADING_LEAVES)
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).east(), TreeModule.LEADING_LEAVES)
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).south(), TreeModule.LEADING_LEAVES)
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).west(), TreeModule.LEADING_LEAVES)

            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).north(2), TreeModule.LEADING_LEAVES)
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge + 1).east(2), TreeModule.LEADING_LEAVES)
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).south(2), TreeModule.LEADING_LEAVES)
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).west(2), TreeModule.LEADING_LEAVES)

            serverLevel.setBlockAndUpdate(blockPos.above(nextAge - 1).north(3), TreeModule.LEADING_LEAVES)
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge + 1).east(3), TreeModule.LEADING_LEAVES)
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge - 1).south(3), TreeModule.LEADING_LEAVES)
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge - 1).west(3), TreeModule.LEADING_LEAVES)
        } else {
            serverLevel.setBlockAndUpdate(
                blockPos.above(nextAge + 1), TreeModule.LEADING_LEAVES
            )
        }
    }

    override fun isRandomlyTicking(blockState: BlockState): Boolean = blockState.getValue(AGE) < MAX_AGE
}