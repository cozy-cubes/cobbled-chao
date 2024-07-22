package com.cozycubes.cobbledchao.trees

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class SeedBlock(properties: Properties) : Block(properties) {
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
            serverLevel.setBlock(blockPos, TreeModule.CHAO_TREE_SAPLING.defaultBlockState(), 2)
            serverLevel.setBlockAndUpdate(blockPos.above(), TreeModule.LEADING_LEAVES)
        }
    }

    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return SHAPE
    }
}