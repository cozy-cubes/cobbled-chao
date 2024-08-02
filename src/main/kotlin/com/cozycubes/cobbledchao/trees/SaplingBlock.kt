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
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty

// TODO: Continue to age until death if relevant to this tree.
class SaplingBlock(val growthStages: GrowthStages, myProperties: Properties) :
    TrunkBlock(myProperties), BonemealableBlock, TreePart {
    // TODO: Datapack this for multiple trees and custom trees.
    companion object {
        // TODO: Reduce tick chance beyond the growth age.
        const val MAX_AGE = 10
        const val MAX_GROWTH_AGE = 5
        val AGE: IntegerProperty = IntegerProperty.create("age", 0, MAX_AGE)
        val FLIP: BooleanProperty = BooleanProperty.create("flip")
    }

    init {
        registerDefaultState(
            stateDefinition.any().setValue(AGE, 0).setValue(SIZE, 0).setValue(U_CONNECT, false)
                .setValue(D_CONNECT, false).setValue(N_CONNECT, false).setValue(E_CONNECT, false)
                .setValue(S_CONNECT, false).setValue(W_CONNECT, false).setValue(MARKED, false)
                .setValue(FLIP, false)
        )
    }

    fun getRandomizedState(): BlockState {
        val flipped = FLIP.possibleValues.random()
        println(flipped)
        return defaultBlockState().setValue(FLIP, flipped)
    }

    override fun codec(): MapCodec<out Block> {
        return CODEC
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(
            AGE, SIZE, U_CONNECT, D_CONNECT, N_CONNECT, E_CONNECT, S_CONNECT, W_CONNECT, MARKED, FLIP
        )
    }

    override fun randomTick(
        blockState: BlockState, serverLevel: ServerLevel, blockPos: BlockPos, randomSource: RandomSource
    ) {
        // TODO: Chance to grow on tick instead of guaranteed.
        grow(serverLevel, blockState, blockPos)
    }

    // TODO: Play particle effect on success/fail.
    fun grow(serverLevel: ServerLevel, blockState: BlockState, blockPos: BlockPos): TreeGrowthResult {
        val age = blockState.getValue(AGE)

        if (age >= MAX_AGE) {
            serverLevel.destroyBlock(blockPos, true)
            cascadeBreakage(serverLevel, blockPos)
            return TreeGrowthResult.PASS
        }

        if (age >= MAX_GROWTH_AGE) {
            serverLevel.setBlock(blockPos, blockState.setValue(AGE, age + 1), 0)
            return TreeGrowthResult.PASS
        }

        return growthStages.applyStage(age, serverLevel, blockState, blockPos)
    }

    override fun isRandomlyTicking(blockState: BlockState): Boolean = true

    override fun isValidBonemealTarget(levelReader: LevelReader, blockPos: BlockPos, blockState: BlockState): Boolean =
        blockState.getValue(AGE) < MAX_GROWTH_AGE

    // TODO: Ensure growth stage can occur.
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