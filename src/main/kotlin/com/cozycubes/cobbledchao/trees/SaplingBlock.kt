package com.cozycubes.cobbledchao.trees

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property
import kotlin.math.min

// TODO: If broken, break all blocks in tree.
// TODO: Continue to age until death if relevant to this tree.
class SaplingBlock(properties: Properties) : Block(properties), BonemealableBlock {
    // TODO: Datapack this for multiple trees and custom trees.
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
        // TODO: Chance to grow on tick instead of guaranteed.
        grow(serverLevel, blockState, blockPos)
    }

    fun grow(serverLevel: ServerLevel, blockState: BlockState, blockPos: BlockPos) {
        val age = blockState.getValue(AGE)
        val nextAge = age + 1
        // TODO: Make sure these aren't already blocks, and if they are, don't do anything.
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
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).north(), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).east(), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).south(), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).west(), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())

            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).north(2), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge + 1).east(2), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).south(2), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge).west(2), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())

            serverLevel.setBlockAndUpdate(blockPos.above(nextAge - 1).north(3), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge + 1).east(3), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge - 1).south(3), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge - 1).west(3), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())

            serverLevel.setBlockAndUpdate(blockPos.above(nextAge-1).north(), TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH))
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge-1).east(), TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.EAST))
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge-1).south(), TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH))
            serverLevel.setBlockAndUpdate(blockPos.above(nextAge-1).west(), TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.WEST))
        } else {
            serverLevel.setBlockAndUpdate(
                blockPos.above(nextAge + 1), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState()
            )
        }
    }

    override fun isRandomlyTicking(blockState: BlockState): Boolean = blockState.getValue(AGE) < MAX_AGE

    override fun isValidBonemealTarget(levelReader: LevelReader, blockPos: BlockPos, blockState: BlockState): Boolean = blockState.getValue(AGE) < MAX_AGE

    override fun isBonemealSuccess(
        level: Level,
        randomSource: RandomSource,
        blockPos: BlockPos,
        blockState: BlockState
    ): Boolean = true

    override fun performBonemeal(
        serverLevel: ServerLevel,
        randomSource: RandomSource,
        blockPos: BlockPos,
        blockState: BlockState
    ) {
        grow(serverLevel, blockState, blockPos)
    }
}