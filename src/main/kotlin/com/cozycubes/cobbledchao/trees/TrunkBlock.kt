package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.trees.Properties.D_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.E_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.N_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.S_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.U_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.W_CONNECT
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property

// TODO: If broken, break all blocks in tree.
class TrunkBlock(properties: Properties) : Block(properties) {
    companion object {
        val SIZE: IntegerProperty = IntegerProperty.create("size", 0, 2)
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