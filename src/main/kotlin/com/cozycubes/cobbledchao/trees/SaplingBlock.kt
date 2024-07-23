package com.cozycubes.cobbledchao.trees

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property

// TODO: If broken, break all blocks in tree.
// TODO: Continue to age until death if relevant to this tree.
class SaplingBlock(properties: Properties) : Block(properties), BonemealableBlock {
    // TODO: Datapack this for multiple trees and custom trees.
    companion object {
        val MAX_AGE = 5
        val MAX_SIZE = 2
        val AGE = IntegerProperty.create("age", 0, MAX_AGE)
        val SIZE = IntegerProperty.create("size", 0, MAX_SIZE)
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(AGE, 0).setValue(SIZE, 0))
    }

    fun getGrowthStages(): List<Map<BlockPos, BlockState>> {
        return listOf<Map<BlockPos, BlockState>>(
            mapOf(
                BlockPos(0, 0, 0) to TreeModule.CHAO_TREE_SAPLING.defaultBlockState()
                    .setValue(SIZE, 1)
                    .setValue(AGE, 1),
                BlockPos(0, 1, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(TrunkBlock.SIZE, 0),
                BlockPos(0, 2, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(TrunkBlock.SIZE, 0),

                BlockPos(1, 2, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(-1, 2, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 2, 1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 2, -1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            ),
            mapOf(
                BlockPos(0, 0, 0) to TreeModule.CHAO_TREE_SAPLING.defaultBlockState()
                    .setValue(SIZE, 2)
                    .setValue(AGE, 2),
                BlockPos(0, 1, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState().setValue(TrunkBlock.SIZE, 1),
                BlockPos(0, 2, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState(),
                BlockPos(0, 3, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState(),

                BlockPos(1, 2, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(-1, 2, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 2, 1) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 2, -1) to Blocks.AIR.defaultBlockState(),

                BlockPos(1, 3, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(-1, 3, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 3, 1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 3, -1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            ),
            mapOf(
                BlockPos(0, 0, 0) to TreeModule.CHAO_TREE_SAPLING.defaultBlockState()
                    .setValue(SIZE, 2)
                    .setValue(AGE, 3),
                BlockPos(0, 1, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState().setValue(TrunkBlock.SIZE, 1),
                BlockPos(0, 2, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState(),
                BlockPos(0, 3, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState(),
                BlockPos(0, 4, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState(),

                BlockPos(1, 3, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(-1, 3, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 3, 1) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 3, -1) to Blocks.AIR.defaultBlockState(),

                BlockPos(1, 4, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(-1, 4, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 4, 1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 4, -1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),

                BlockPos(2, 5, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(-2, 3, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 3, 2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 3, -2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            ),
            mapOf(
                BlockPos(0, 0, 0) to TreeModule.CHAO_TREE_SAPLING.defaultBlockState()
                    .setValue(SIZE, 2)
                    .setValue(AGE, 4),
                BlockPos(0, 5, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState(),

                BlockPos(1, 4, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(-1, 4, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 4, 1) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 4, -1) to Blocks.AIR.defaultBlockState(),

                BlockPos(2, 5, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(-2, 3, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 3, 2) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 3, -2) to Blocks.AIR.defaultBlockState(),

                BlockPos(1, 5, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(-1, 5, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 5, 1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 5, -1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),

                BlockPos(2, 6, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(-2, 4, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 4, 2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 4, -2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            ),
            mapOf(
                BlockPos(0, 0, 0) to TreeModule.CHAO_TREE_SAPLING.defaultBlockState()
                    .setValue(SIZE, 2)
                    .setValue(AGE, 5),
                BlockPos(0, 6, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState(),

                BlockPos(1, 5, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(-1, 5, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 5, 1) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 5, -1) to Blocks.AIR.defaultBlockState(),

                BlockPos(2, 6, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(-2, 4, 0) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 4, 2) to Blocks.AIR.defaultBlockState(),
                BlockPos(0, 4, -2) to Blocks.AIR.defaultBlockState(),

                BlockPos(1, 6, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(-1, 6, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 6, 1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 6, -1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),

                BlockPos(2, 6, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(-2, 6, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 6, 2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 6, -2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),

                BlockPos(3, 7, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(-3, 5, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 5, 3) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 5, -3) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            )
        )
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
        val nextAge = blockState.getValue(AGE)

        val growthStage = getGrowthStages()[nextAge]
        if (growthStage.entries.any { (offset) ->
                val target = serverLevel.getBlockState(blockPos.offset(offset))
                return@any !(target.`is`(TreeModule.CHAO_TREE_FRUIT_BLOCK)
                        || target.`is`(TreeModule.CHAO_TREE_SAPLING)
                        || target.`is`(TreeModule.CHAO_TREE_TRUNK)
                        || target.`is`(TreeModule.CHAO_TREE_LEAVES_BLOCK)
                        || target.isAir)
            }) {
            return
        }
        growthStage.entries.forEach { (offset, newState) ->
            serverLevel.setBlockAndUpdate(blockPos.offset(offset), newState)
        }
    }

    override fun isRandomlyTicking(blockState: BlockState): Boolean = blockState.getValue(AGE) < MAX_AGE

    override fun isValidBonemealTarget(levelReader: LevelReader, blockPos: BlockPos, blockState: BlockState): Boolean =
        blockState.getValue(AGE) < MAX_AGE

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