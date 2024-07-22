package com.cozycubes.cobbledchao.trees

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property

class TrunkBlock(properties: Properties) : Block(properties) {
    companion object {
        val SIZE = IntegerProperty.create("size", 0, 2)
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(SIZE, 0))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(*arrayOf<Property<*>>(SIZE))
    }
}