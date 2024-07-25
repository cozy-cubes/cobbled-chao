@file:Suppress("MemberVisibilityCanBePrivate")

package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.trees.Properties.D_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.E_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.MARKED
import com.cozycubes.cobbledchao.trees.Properties.N_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.S_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.U_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.W_CONNECT
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property

// TODO: If broken, break all blocks in tree.
// TODO: Continue to age until death if relevant to this tree.
class SaplingBlock(properties: Properties) : TrunkBlock(properties), BonemealableBlock, TreePart {
    // TODO: Datapack this for multiple trees and custom trees.
    companion object {
        const val MAX_AGE = 5
        val AGE: IntegerProperty = IntegerProperty.create("age", 0, MAX_AGE)
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(SIZE, 0)
                .setValue(U_CONNECT, false)
                .setValue(D_CONNECT, false)
                .setValue(N_CONNECT, false)
                .setValue(E_CONNECT, false)
                .setValue(S_CONNECT, false)
                .setValue(W_CONNECT, false)
                .setValue(MARKED, false)
        )
    }

    // TODO: Abstract and datapack.
    fun getGrowthStages(): List<Map<BlockPos, BlockState>> {
        return listOf<Map<BlockPos, BlockState>>(
            mapOf(
                BlockPos(0, 0, 0) to TreeModule.CHAO_TREE_SAPLING.defaultBlockState()
                    .setValue(SIZE, 1)
                    .setValue(AGE, 1)
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 1, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(TrunkBlock.SIZE, 0)
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 2, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(TrunkBlock.SIZE, 0)
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true)
                    .setValue(N_CONNECT, true)
                    .setValue(E_CONNECT, true)
                    .setValue(S_CONNECT, true)
                    .setValue(W_CONNECT, true),

                BlockPos(1, 2, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(-1, 2, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 2, 1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
                BlockPos(0, 2, -1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            ),
            mapOf(
                BlockPos(0, 0, 0) to TreeModule.CHAO_TREE_SAPLING.defaultBlockState()
                    .setValue(SIZE, 2)
                    .setValue(AGE, 2)
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 1, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(TrunkBlock.SIZE, 1)
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 2, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 3, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true)
                    .setValue(N_CONNECT, true)
                    .setValue(E_CONNECT, true)
                    .setValue(S_CONNECT, true)
                    .setValue(W_CONNECT, true),

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
                    .setValue(AGE, 3)
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 1, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(TrunkBlock.SIZE, 1)
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 2, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 3, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 4, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true)
                    .setValue(N_CONNECT, true)
                    .setValue(E_CONNECT, true)
                    .setValue(S_CONNECT, true)
                    .setValue(W_CONNECT, true),

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
                    .setValue(AGE, 4)
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 4, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 5, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true)
                    .setValue(N_CONNECT, true)
                    .setValue(E_CONNECT, true)
                    .setValue(S_CONNECT, true)
                    .setValue(W_CONNECT, true),

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
                    .setValue(AGE, 5)
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 5, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true),
                BlockPos(0, 6, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                    .setValue(U_CONNECT, true)
                    .setValue(D_CONNECT, true)
                    .setValue(N_CONNECT, true)
                    .setValue(E_CONNECT, true)
                    .setValue(S_CONNECT, true)
                    .setValue(W_CONNECT, true),

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

                BlockPos(1, 5, 0) to TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState()
                    .setValue(FACING, Direction.EAST),
                BlockPos(-1, 5, 0) to TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState()
                    .setValue(FACING, Direction.WEST),
                BlockPos(0, 5, 1) to TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState()
                    .setValue(FACING, Direction.SOUTH),
                BlockPos(0, 5, -1) to TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState()
                    .setValue(FACING, Direction.NORTH),
            )
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(
            *arrayOf<Property<*>>(
                AGE,
                SIZE,
                U_CONNECT,
                D_CONNECT,
                N_CONNECT,
                E_CONNECT,
                S_CONNECT,
                W_CONNECT,
                MARKED
            )
        )
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