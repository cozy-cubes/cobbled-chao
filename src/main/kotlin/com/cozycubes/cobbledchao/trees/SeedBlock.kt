package com.cozycubes.cobbledchao.trees

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.BushBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class SeedBlock(properties: Properties) : BushBlock(properties), BonemealableBlock {
    companion object {
        val CODEC: MapCodec<SeedBlock> = simpleCodec(::SeedBlock)
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

    override fun codec(): MapCodec<out BushBlock> {
        return CODEC
    }

    fun grow(serverLevel: ServerLevel, blockPos: BlockPos) {
        serverLevel.setBlock(blockPos, TreeModule.CHAO_TREE_SAPLING.defaultBlockState(), 2)
        TreeModule.CHAO_TREE_SAPLING.performBonemeal(
            serverLevel,
            serverLevel.random,
            blockPos,
            TreeModule.CHAO_TREE_SAPLING.defaultBlockState()
        )
    }

    override fun mayPlaceOn(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): Boolean {
        return blockState.`is`(BlockTags.DIRT)
    }

    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape = SHAPE

    override fun isValidBonemealTarget(levelReader: LevelReader, blockPos: BlockPos, blockState: BlockState): Boolean =
        true

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