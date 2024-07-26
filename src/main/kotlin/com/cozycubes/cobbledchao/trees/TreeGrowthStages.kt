package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.trees.Properties.D_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.E_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.N_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.S_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.U_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.W_CONNECT
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING
import net.minecraft.world.level.block.state.BlockState

object TreeGrowthStages {
    val gardenTreeGrowthStages = listOf<Map<BlockPos, BlockState>>(
        mapOf(
            BlockPos(0, 1, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(TrunkBlock.SIZE, 0)
                .setValue(U_CONNECT, true)
                .setValue(D_CONNECT, true),
            BlockPos(0, 2, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(TrunkBlock.SIZE, 0)
                .setValue(D_CONNECT, true)
                .setValue(N_CONNECT, true)
                .setValue(E_CONNECT, true)
                .setValue(S_CONNECT, true)
                .setValue(W_CONNECT, true),

            BlockPos(1, 2, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(-1, 2, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 2, 1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 2, -1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
        ),
        mapOf(
            BlockPos(0, 1, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(TrunkBlock.SIZE, 1)
                .setValue(U_CONNECT, true)
                .setValue(D_CONNECT, true),
            BlockPos(0, 2, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(U_CONNECT, true)
                .setValue(D_CONNECT, true),
            BlockPos(0, 3, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(D_CONNECT, true)
                .setValue(N_CONNECT, true)
                .setValue(E_CONNECT, true)
                .setValue(S_CONNECT, true)
                .setValue(W_CONNECT, true),

            BlockPos(1, 2, 0) to Blocks.AIR.defaultBlockState(),
            BlockPos(-1, 2, 0) to Blocks.AIR.defaultBlockState(),
            BlockPos(0, 2, 1) to Blocks.AIR.defaultBlockState(),
            BlockPos(0, 2, -1) to Blocks.AIR.defaultBlockState(),

            BlockPos(1, 3, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(-1, 3, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 3, 1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 3, -1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
        ),
        mapOf(
            BlockPos(0, 1, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(TrunkBlock.SIZE, 1)
                .setValue(U_CONNECT, true)
                .setValue(D_CONNECT, true),
            BlockPos(0, 2, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(U_CONNECT, true)
                .setValue(D_CONNECT, true),
            BlockPos(0, 3, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(U_CONNECT, true)
                .setValue(D_CONNECT, true),
            BlockPos(0, 4, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(D_CONNECT, true)
                .setValue(N_CONNECT, true)
                .setValue(E_CONNECT, true)
                .setValue(S_CONNECT, true)
                .setValue(W_CONNECT, true),

            BlockPos(1, 3, 0) to Blocks.AIR.defaultBlockState(),
            BlockPos(-1, 3, 0) to Blocks.AIR.defaultBlockState(),
            BlockPos(0, 3, 1) to Blocks.AIR.defaultBlockState(),
            BlockPos(0, 3, -1) to Blocks.AIR.defaultBlockState(),

            BlockPos(1, 4, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(-1, 4, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 4, 1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 4, -1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),

            BlockPos(2, 5, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(-2, 3, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 3, 2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 3, -2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
        ),
        mapOf(
            BlockPos(0, 4, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(U_CONNECT, true)
                .setValue(D_CONNECT, true),
            BlockPos(0, 5, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(D_CONNECT, true)
                .setValue(N_CONNECT, true)
                .setValue(E_CONNECT, true)
                .setValue(S_CONNECT, true)
                .setValue(W_CONNECT, true),

            BlockPos(1, 4, 0) to Blocks.AIR.defaultBlockState(),
            BlockPos(-1, 4, 0) to Blocks.AIR.defaultBlockState(),
            BlockPos(0, 4, 1) to Blocks.AIR.defaultBlockState(),
            BlockPos(0, 4, -1) to Blocks.AIR.defaultBlockState(),

            BlockPos(2, 5, 0) to Blocks.AIR.defaultBlockState(),
            BlockPos(-2, 3, 0) to Blocks.AIR.defaultBlockState(),
            BlockPos(0, 3, 2) to Blocks.AIR.defaultBlockState(),
            BlockPos(0, 3, -2) to Blocks.AIR.defaultBlockState(),

            BlockPos(1, 5, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(-1, 5, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 5, 1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 5, -1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),

            BlockPos(2, 6, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(-2, 4, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 4, 2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 4, -2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
        ),
        mapOf(
            BlockPos(0, 5, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(U_CONNECT, true)
                .setValue(D_CONNECT, true),
            BlockPos(0, 6, 0) to TreeModule.CHAO_TREE_TRUNK.defaultBlockState()
                .setValue(D_CONNECT, true)
                .setValue(N_CONNECT, true)
                .setValue(E_CONNECT, true)
                .setValue(S_CONNECT, true)
                .setValue(W_CONNECT, true),

            BlockPos(2, 6, 0) to Blocks.AIR.defaultBlockState(),
            BlockPos(-2, 4, 0) to Blocks.AIR.defaultBlockState(),
            BlockPos(0, 4, 2) to Blocks.AIR.defaultBlockState(),
            BlockPos(0, 4, -2) to Blocks.AIR.defaultBlockState(),

            BlockPos(1, 6, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(-1, 6, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 6, 1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 6, -1) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),

            BlockPos(2, 6, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(-2, 6, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 6, 2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 6, -2) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),

            BlockPos(3, 7, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(-3, 5, 0) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 5, 3) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),
            BlockPos(0, 5, -3) to TreeModule.CHAO_TREE_LEAVES_BLOCK.defaultBlockState(),

            BlockPos(1, 5, 0) to TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState()
                .setValue(FACING, Direction.EAST),
            BlockPos(-1, 5, 0) to TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState()
                .setValue(FACING, Direction.WEST),
            BlockPos(0, 5, 1) to TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState()
                .setValue(FACING, Direction.SOUTH),
            BlockPos(0, 5, -1) to TreeModule.CHAO_TREE_FRUIT_BLOCK.defaultBlockState()
                .setValue(FACING, Direction.NORTH),
        )
    )
}