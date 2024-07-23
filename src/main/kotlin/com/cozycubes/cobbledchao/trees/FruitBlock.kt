@file:Suppress("MemberVisibilityCanBePrivate")

package com.cozycubes.cobbledchao.trees

import com.mojang.serialization.MapCodec
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

// TODO: Drop item similar to cocoa block logic.
class FruitBlock(properties: Properties) : HorizontalDirectionalBlock(properties), BonemealableBlock {
    companion object {
        val CODEC: MapCodec<FruitBlock> = simpleCodec(::FruitBlock)
        const val MAX_AGE = 3
        val AGE: IntegerProperty = IntegerProperty.create("age", 0, MAX_AGE)
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(AGE, 0))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(*arrayOf<Property<*>>(FACING, AGE))
    }

    override fun codec(): MapCodec<out HorizontalDirectionalBlock> {
        return CODEC
    }

    override fun randomTick(
        blockState: BlockState,
        serverLevel: ServerLevel,
        blockPos: BlockPos,
        randomSource: RandomSource
    ) {
        serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(AGE, blockState.getValue(AGE) + 1))
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
        serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(AGE, blockState.getValue(AGE) + 1))
    }
}