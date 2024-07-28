@file:Suppress("MemberVisibilityCanBePrivate")

package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.trees.Properties.D_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.E_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.MARKED
import com.cozycubes.cobbledchao.trees.Properties.N_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.SIZE
import com.cozycubes.cobbledchao.trees.Properties.S_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.U_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.W_CONNECT
import com.google.common.primitives.Ints.min
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property

// TODO: Continue to age until death if relevant to this tree.
class SaplingBlock(val growthStages: List<List<GrowthStageEntry>>, myProperties: Properties) :
    TrunkBlock(myProperties), BonemealableBlock, TreePart {
    // TODO: Datapack this for multiple trees and custom trees.
    companion object {
        const val MAX_AGE = 60
        const val MAX_GROWTH_AGE = 5
        val AGE: IntegerProperty = IntegerProperty.create("age", 0, MAX_AGE)
    }

    init {
        registerDefaultState(
            stateDefinition.any().setValue(AGE, 0).setValue(SIZE, 0).setValue(U_CONNECT, false)
                .setValue(D_CONNECT, false).setValue(N_CONNECT, false).setValue(E_CONNECT, false)
                .setValue(S_CONNECT, false).setValue(W_CONNECT, false).setValue(MARKED, false)
        )
    }

    override fun codec(): MapCodec<out Block> {
        return CODEC
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(
            *arrayOf<Property<*>>(
                AGE, SIZE, U_CONNECT, D_CONNECT, N_CONNECT, E_CONNECT, S_CONNECT, W_CONNECT, MARKED
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
        val age = blockState.getValue(AGE)

        if (age >= MAX_AGE) {
            serverLevel.destroyBlock(blockPos, true)
            cascadeBreakage(serverLevel, blockPos)
            return
        }

        if (age >= MAX_GROWTH_AGE) {
            serverLevel.setBlock(blockPos, blockState.setValue(AGE, age + 1), 0)
            return
        }

        val growthStage = growthStages[age]
        if (growthStage.any { growthStageEntry ->
                val offset = growthStageEntry.pos
                val target = serverLevel.getBlockState(blockPos.offset(offset))
                return@any !(target.block is FruitBlock || target.block is SaplingBlock || target.block is TrunkBlock || target.block is LeafBlock || target.isAir)
            }) {
            return
        }
        growthStage.forEach { growthStageEntry ->
            val offset = growthStageEntry.pos
            val state = growthStageEntry.evaluate()
            serverLevel.setBlockAndUpdate(blockPos.offset(offset), state)
        }
        serverLevel.setBlockAndUpdate(
            blockPos,
            blockState.setValue(AGE, age + 1).setValue(SIZE, min(age + 1, SIZE.possibleValues.max()))
                .setValue(U_CONNECT, true).setValue(D_CONNECT, true)
        )
    }

    override fun isRandomlyTicking(blockState: BlockState): Boolean = true

    override fun isValidBonemealTarget(levelReader: LevelReader, blockPos: BlockPos, blockState: BlockState): Boolean =
        blockState.getValue(AGE) < MAX_GROWTH_AGE

    override fun isBonemealSuccess(
        level: Level, randomSource: RandomSource, blockPos: BlockPos, blockState: BlockState
    ): Boolean = true

    override fun performBonemeal(
        serverLevel: ServerLevel, randomSource: RandomSource, blockPos: BlockPos, blockState: BlockState
    ) {
        grow(serverLevel, blockState, blockPos)
    }

    override fun tick(
        blockState: BlockState, serverLevel: ServerLevel, blockPos: BlockPos, randomSource: RandomSource
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