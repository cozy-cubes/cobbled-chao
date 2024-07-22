package com.cozycubes.cobbledchao.trees

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

// TODO: Should only be plant-able on soil
class SeedBlock(properties: Properties) : Block(properties), BonemealableBlock {
    companion object {
        // TODO: Config this.
        const val REQUIRED_LIGHT = 9

        // TODO: Config this.
        const val GROWTH_CHANCE = 7

        val SHAPE = box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0)
    }

    override fun randomTick(
        blockState: BlockState, serverLevel: ServerLevel, blockPos: BlockPos, randomSource: RandomSource
    ) {
        if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= REQUIRED_LIGHT && randomSource.nextInt(
                GROWTH_CHANCE
            ) == 0
        ) {
            grow(serverLevel, blockPos)
        }
    }

    fun grow(serverLevel: ServerLevel, blockPos: BlockPos) {
        serverLevel.setBlock(blockPos, TreeModule.CHAO_TREE_SAPLING.defaultBlockState(), 2)
        serverLevel.setBlockAndUpdate(blockPos.above(), TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState())
    }

    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return SHAPE
    }

    override fun isValidBonemealTarget(levelReader: LevelReader, blockPos: BlockPos, blockState: BlockState): Boolean = true

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
        grow(serverLevel, blockPos)
    }
}