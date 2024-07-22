package com.cozycubes.cobbledchao.trees

import com.mojang.serialization.MapCodec
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.Property

class FruitBlock(properties: Properties) : HorizontalDirectionalBlock(properties) {
    companion object {
        val CODEC = simpleCodec(::FruitBlock)
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        // TODO: Clean up the model and its facings.
        builder.add(*arrayOf<Property<*>>(FACING))
    }

    override fun codec(): MapCodec<out HorizontalDirectionalBlock> {
        return CODEC
    }
}