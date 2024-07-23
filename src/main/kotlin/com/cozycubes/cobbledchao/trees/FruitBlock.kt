@file:Suppress("MemberVisibilityCanBePrivate")

package com.cozycubes.cobbledchao.trees

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.BlockHitResult

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
        serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(AGE, blockState.getValue(AGE) + 1))
    }

    override fun useItemOn(
        itemStack: ItemStack,
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        interactionHand: InteractionHand,
        blockHitResult: BlockHitResult
    ): ItemInteractionResult {
        if (interactionHand != InteractionHand.MAIN_HAND || level.isClientSide || blockState.getValue(AGE) < MAX_AGE) {
            return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult)
        }
        harvest(level as ServerLevel, blockPos, player as ServerPlayer)
        return ItemInteractionResult.SUCCESS
    }

    override fun useWithoutItem(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        blockHitResult: BlockHitResult
    ): InteractionResult {
        return super.useWithoutItem(blockState, level, blockPos, player, blockHitResult)
    }

    fun harvest(serverLevel: ServerLevel, blockPos: BlockPos, player: ServerPlayer) {
        serverLevel.setBlockAndUpdate(blockPos, TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState())
        player.addItem(ItemStack(TreeModule.CHAO_TREE_FRUIT))
    }
}