@file:Suppress("MemberVisibilityCanBePrivate")

package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.trees.Properties.MARKED
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class FruitBlock(val fruitItem: Item, properties: Properties) : Block(properties), BonemealableBlock, TreePart {
    companion object {
        const val MAX_AGE = 3
        val AGE: IntegerProperty = IntegerProperty.create("age", 0, MAX_AGE)

        val SHAPES = mapOf<Direction, List<VoxelShape>>(
            Direction.NORTH to listOf(
                box(6.5, 9.0, 14.5, 9.5, 13.0, 17.5),
                box(6.5, 8.0, 14.5, 9.5, 13.0, 17.5),
                box(5.5, 7.0, 13.5, 10.5, 13.0, 18.5),
                box(5.5, 6.0, 13.0, 11.0, 13.0, 19.0)
            ),
            Direction.EAST to listOf(
                box(-1.5, 9.0, 6.5, 1.5, 13.0, 9.5),
                box(-1.5, 8.0, 6.5, 1.5, 13.0, 9.5),
                box(-2.5, 7.0, 5.5, 2.5, 13.0, 10.5),
                box(-3.0, 6.0, 5.0, 3.0, 13.0, 9.0)
            ),
            Direction.SOUTH to listOf(
                box(6.5, 9.0, -1.5, 9.5, 13.0, 1.5),
                box(6.5, 8.0, -1.5, 9.5, 13.0, 1.5),
                box(5.5, 7.0, -2.5, 10.5, 13.0, 2.5),
                box(5.0, 6.0, -3.0, 11.0, 13.0, 3.0)
            ),
            Direction.WEST to listOf(
                box(14.5, 9.0, 6.5, 17.5, 13.0, 9.5),
                box(14.5, 8.0, 6.5, 17.5, 13.0, 9.5),
                box(13.5, 7.0, 5.5, 18.5, 13.0, 10.5),
                box(13.0, 6.0, 5.0, 19.0, 13.0, 11.0)
            )
        )
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(AGE, 0))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(*arrayOf<Property<*>>(HORIZONTAL_FACING, AGE, MARKED))
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
        harvest(level as ServerLevel, blockPos, player as ServerPlayer, blockState)
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

    fun harvest(serverLevel: ServerLevel, blockPos: BlockPos, player: ServerPlayer, blockState: BlockState) {
        serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(AGE, 0))
        player.addItem(ItemStack(fruitItem))
    }

    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        val facing = blockState.getValue(HORIZONTAL_FACING)
        val age = blockState.getValue(AGE)
        return SHAPES[facing]!![age]
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