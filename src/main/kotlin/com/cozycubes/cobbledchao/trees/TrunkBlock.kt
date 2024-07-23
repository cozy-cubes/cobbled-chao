package com.cozycubes.cobbledchao.trees

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property

// TODO: If broken, break all blocks in tree.
// TODO: Connect to nearby leaves blocks horizontally.
class TrunkBlock(properties: Properties) : Block(properties) {
    companion object {
        val SIZE = IntegerProperty.create("size", 0, 2)
        val U_CONNECT = BooleanProperty.create("up")
        val D_CONNECT = BooleanProperty.create("down")
        val N_CONNECT = BooleanProperty.create("north")
        val E_CONNECT = BooleanProperty.create("east")
        val S_CONNECT = BooleanProperty.create("south")
        val W_CONNECT = BooleanProperty.create("west")
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(SIZE, 0)
                .setValue(U_CONNECT, false)
                .setValue(D_CONNECT, false)
                .setValue(N_CONNECT, false)
                .setValue(E_CONNECT, false)
                .setValue(S_CONNECT, false)
                .setValue(W_CONNECT, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(*arrayOf<Property<*>>(SIZE, U_CONNECT, D_CONNECT, N_CONNECT, E_CONNECT, S_CONNECT, W_CONNECT))
    }
}