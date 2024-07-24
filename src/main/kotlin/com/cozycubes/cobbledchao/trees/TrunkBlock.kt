package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.trees.Properties.D_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.E_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.N_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.S_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.U_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.W_CONNECT
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

// TODO: If broken, break all blocks in tree.
open class TrunkBlock(properties: Properties) : Block(properties) {
    companion object {
        val SIZE: IntegerProperty = IntegerProperty.create("size", 0, 2)

        val CACHED_SHAPES = mutableMapOf<BlockState, VoxelShape>()
        val CENTER_SHAPES = listOf<VoxelShape>(
            box(4.0, 4.0, 4.0, 12.0, 12.0, 12.0),
            box(3.0, 3.0, 3.0, 13.0, 13.0, 13.0),
            box(2.0, 2.0, 2.0, 14.0, 14.0, 14.0)
        )
        val SIDE_SHAPES = mapOf<Direction, List<VoxelShape>>(
            Direction.UP to listOf(
                box(4.0, 12.0, 4.0, 12.0, 16.0, 12.0),
                box(3.0, 11.0, 3.0, 13.0, 16.0, 13.0),
                box(2.0, 10.0, 2.0, 14.0, 16.0, 14.0)
            ),
            Direction.DOWN to listOf(
                box(4.0, 0.0, 4.0, 12.0, 4.0, 12.0),
                box(3.0, 0.0, 3.0, 13.0, 3.0, 13.0),
                box(2.0, 0.0, 2.0, 14.0, 2.0, 14.0)
            ),
            Direction.NORTH to listOf(
                box(4.0, 4.0, 0.0, 12.0, 12.0, 4.0),
                box(3.0, 3.0, 0.0, 13.0, 13.0, 3.0),
                box(2.0, 2.0, 0.0, 14.0, 14.0, 2.0)
            ),
            Direction.EAST to listOf(
                box(12.0, 4.0, 4.0, 16.0, 12.0, 12.0),
                box(11.0, 3.0, 3.0, 16.0, 13.0, 13.0),
                box(10.0, 2.0, 2.0, 16.0, 14.0, 14.0)
            ),
            Direction.SOUTH to listOf(
                box(4.0, 4.0, 12.0, 12.0, 12.0, 16.0),
                box(3.0, 3.0, 11.0, 13.0, 13.0, 16.0),
                box(2.0, 2.0, 10.0, 14.0, 14.0, 16.0)
            ),
            Direction.WEST to listOf(
                box(0.0, 4.0, 4.0, 4.0, 12.0, 12.0),
                box(0.0, 3.0, 3.0, 3.0, 13.0, 13.0),
                box(0.0, 2.0, 2.0, 2.0, 14.0, 14.0)
            )
        )
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

    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        CACHED_SHAPES[blockState]?.let {
            return it
        }

        val age = blockState.getValue(SIZE)
        var shape = CENTER_SHAPES[age]
        if (blockState.getValue(U_CONNECT))
            shape = Shapes.or(shape, SIDE_SHAPES[Direction.UP]!![age])
        if (blockState.getValue(D_CONNECT))
            shape = Shapes.or(shape, SIDE_SHAPES[Direction.DOWN]!![age])
        if (blockState.getValue(N_CONNECT))
            shape = Shapes.or(shape, SIDE_SHAPES[Direction.NORTH]!![age])
        if (blockState.getValue(E_CONNECT))
            shape = Shapes.or(shape, SIDE_SHAPES[Direction.EAST]!![age])
        if (blockState.getValue(S_CONNECT))
            shape = Shapes.or(shape, SIDE_SHAPES[Direction.SOUTH]!![age])
        if (blockState.getValue(W_CONNECT))
            shape = Shapes.or(shape, SIDE_SHAPES[Direction.WEST]!![age])

        CACHED_SHAPES[blockState] = shape
        return shape
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(*arrayOf<Property<*>>(SIZE, U_CONNECT, D_CONNECT, N_CONNECT, E_CONNECT, S_CONNECT, W_CONNECT))
    }
}