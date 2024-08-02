package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.extensions.flip
import com.cozycubes.cobbledchao.trees.Properties.D_CONNECT
import com.cozycubes.cobbledchao.trees.Properties.SIZE
import com.cozycubes.cobbledchao.trees.Properties.U_CONNECT
import com.cozycubes.cobbledchao.trees.SaplingBlock.Companion.AGE
import com.cozycubes.cobbledchao.trees.SaplingBlock.Companion.FLIP
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import kotlin.math.min

enum class GrowthStageBlock {
    SAPLING, TRUNK, LEAVES, FRUIT, AIR
}

enum class TreeGrowthResult {
    FAIL, SUCCESS, PASS
}

data class GrowthStageEntry(
    val pos: BlockPos,
    val block: GrowthStageBlock,
    val switches: Map<String, Boolean> = emptyMap(),
    val counts: Map<String, Int> = emptyMap(),
    val facing: Direction? = null
) {
    fun evaluate(matchers: Map<GrowthStageBlock, String>): BlockState {
        var retval = BuiltInRegistries.BLOCK[ResourceLocation.parse(matchers[block]!!)].defaultBlockState()

        retval = switches.entries.fold(retval) { acc, (name, value) ->
            return@fold acc.setValue(Properties.propsByName[name] as BooleanProperty, value)
        }

        retval = counts.entries.fold(retval) { acc, (name, value) ->
            return@fold acc.setValue(Properties.propsByName[name] as IntegerProperty, value)
        }

        if (facing != null) {
            retval = retval.setValue(HORIZONTAL_FACING, facing)
        }

        return retval
    }
}

data class GrowthStages(
    val treeName: String,
    val sapling: String,
    val trunk: String,
    val leaves: String,
    val fruit: String,
    val pools: List<List<GrowthStageEntry>>
) {
    val matchers = mapOf(
        GrowthStageBlock.SAPLING to sapling,
        GrowthStageBlock.TRUNK to trunk,
        GrowthStageBlock.LEAVES to leaves,
        GrowthStageBlock.FRUIT to fruit,
        GrowthStageBlock.AIR to "air"
    )

    fun applyStage(age: Int, level: ServerLevel, saplingState: BlockState, saplingPos: BlockPos): TreeGrowthResult {
        val flipped = saplingState.getValue(FLIP)
        val growthStage = pools[age]

        if (growthStage.any { growthStageEntry ->
                val offset = growthStageEntry.pos.flip(flipped)
                val target = level.getBlockState(saplingPos.offset(offset))
                return@any !(target.block is FruitBlock || target.block is SaplingBlock || target.block is TrunkBlock || target.block is LeafBlock || target.isAir)
            }) {
            return TreeGrowthResult.FAIL
        }

        growthStage.forEach { growthStageEntry ->
            val offset = growthStageEntry.pos.flip(flipped)
            val entryState = growthStageEntry.evaluate(matchers)
            val entryPos = saplingPos.offset(offset)
            level.setBlockAndUpdate(entryPos, entryState)
        }

        level.setBlockAndUpdate(
            saplingPos,
            saplingState.setValue(AGE, age + 1).setValue(SIZE, min(age + 1, SIZE.possibleValues.max()))
                .setValue(U_CONNECT, true).setValue(D_CONNECT, true)
        )

        return TreeGrowthResult.SUCCESS
    }
}

object TreeGrowthStages {
    val gardenTreeGrowthStages = GrowthStages(
        "garden",
        "cobbled-chao:chaotree_garden_sapling",
        "cobbled-chao:chaotree_garden_trunk",
        "cobbled-chao:chaotree_garden_leaves",
        "cobbled-chao:chaotree_garden_fruit",
        listOf(
            listOf(
                GrowthStageEntry(
                    BlockPos(0, 1, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "up" to true,
                        "down" to true
                    )
                ),
                GrowthStageEntry(
                    BlockPos(0, 2, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "down" to true,
                        "north" to true,
                        "east" to true,
                        "south" to true,
                        "west" to true
                    )
                ),

                GrowthStageEntry(
                    BlockPos(1, 2, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(-1, 2, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 2, 1),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 2, -1),
                    GrowthStageBlock.LEAVES
                ),
            ),
            listOf(
                GrowthStageEntry(
                    BlockPos(0, 1, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "up" to true,
                        "down" to true,
                    ),
                    mapOf(
                        "size" to 1
                    )
                ),
                GrowthStageEntry(
                    BlockPos(0, 2, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "up" to true,
                        "down" to true,
                    ),
                ),
                GrowthStageEntry(
                    BlockPos(0, 3, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "down" to true,
                        "north" to true,
                        "east" to true,
                        "south" to true,
                        "west" to true,
                    ),
                ),

                GrowthStageEntry(
                    BlockPos(1, 2, 0),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(-1, 2, 0),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(0, 2, 1),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(0, 2, -1),
                    GrowthStageBlock.AIR
                ),

                GrowthStageEntry(
                    BlockPos(1, 3, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(-1, 3, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 3, 1),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 3, -1),
                    GrowthStageBlock.LEAVES
                ),
            ),
            listOf(
                GrowthStageEntry(
                    BlockPos(0, 1, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "up" to true,
                        "down" to true
                    ),
                    mapOf(
                        "size" to 1
                    )
                ),
                GrowthStageEntry(
                    BlockPos(0, 2, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "up" to true,
                        "down" to true
                    ),
                ),
                GrowthStageEntry(
                    BlockPos(0, 3, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "up" to true,
                        "down" to true
                    ),
                ),
                GrowthStageEntry(
                    BlockPos(0, 4, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "down" to true,
                        "north" to true,
                        "east" to true,
                        "south" to true,
                        "west" to true,
                    ),
                ),

                GrowthStageEntry(
                    BlockPos(1, 3, 0),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(-1, 3, 0),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(0, 3, 1),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(0, 3, -1),
                    GrowthStageBlock.AIR
                ),

                GrowthStageEntry(
                    BlockPos(1, 4, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(-1, 4, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 4, 1),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 4, -1),
                    GrowthStageBlock.LEAVES
                ),

                GrowthStageEntry(
                    BlockPos(2, 5, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(-2, 3, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 3, 2),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 3, -2),
                    GrowthStageBlock.LEAVES
                ),
            ),
            listOf(
                GrowthStageEntry(
                    BlockPos(0, 4, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "up" to true,
                        "down" to true,
                    )
                ),
                GrowthStageEntry(
                    BlockPos(0, 5, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "down" to true,
                        "north" to true,
                        "east" to true,
                        "south" to true,
                        "west" to true
                    )
                ),

                GrowthStageEntry(
                    BlockPos(1, 4, 0),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(-1, 4, 0),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(0, 4, 1),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(0, 4, -1),
                    GrowthStageBlock.AIR
                ),

                GrowthStageEntry(
                    BlockPos(2, 5, 0),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(-2, 3, 0),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(0, 3, 2),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(0, 3, -2),
                    GrowthStageBlock.AIR
                ),

                GrowthStageEntry(
                    BlockPos(1, 5, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(-1, 5, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 5, 1),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 5, -1),
                    GrowthStageBlock.LEAVES
                ),

                GrowthStageEntry(
                    BlockPos(2, 6, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(-2, 4, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 4, 2),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 4, -2),
                    GrowthStageBlock.LEAVES
                ),
            ),
            listOf(
                GrowthStageEntry(
                    BlockPos(0, 5, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "up" to true,
                        "down" to true,
                    ),
                ),
                GrowthStageEntry(
                    BlockPos(0, 6, 0),
                    GrowthStageBlock.TRUNK,
                    mapOf(
                        "down" to true,
                        "north" to true,
                        "east" to true,
                        "south" to true,
                        "west" to true
                    )
                ),

                GrowthStageEntry(
                    BlockPos(2, 6, 0),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(-2, 4, 0),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(0, 4, 2),
                    GrowthStageBlock.AIR
                ),
                GrowthStageEntry(
                    BlockPos(0, 4, -2),
                    GrowthStageBlock.AIR
                ),

                GrowthStageEntry(
                    BlockPos(1, 6, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(-1, 6, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 6, 1),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 6, -1),
                    GrowthStageBlock.LEAVES
                ),

                GrowthStageEntry(
                    BlockPos(2, 6, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(-2, 6, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 6, 2),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 6, -2),
                    GrowthStageBlock.LEAVES
                ),

                GrowthStageEntry(
                    BlockPos(3, 7, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(-3, 5, 0),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 5, 3),
                    GrowthStageBlock.LEAVES
                ),
                GrowthStageEntry(
                    BlockPos(0, 5, -3),
                    GrowthStageBlock.LEAVES
                ),

                GrowthStageEntry(
                    BlockPos(1, 5, 0),
                    GrowthStageBlock.FRUIT,
                    facing = Direction.EAST
                ),
                GrowthStageEntry(
                    BlockPos(-1, 5, 0),
                    GrowthStageBlock.FRUIT,
                    facing = Direction.WEST
                ),
                GrowthStageEntry(
                    BlockPos(0, 5, 1),
                    GrowthStageBlock.FRUIT,
                    facing = Direction.SOUTH
                ),
                GrowthStageEntry(
                    BlockPos(0, 5, -1),
                    GrowthStageBlock.FRUIT,
                    facing = Direction.NORTH
                ),
            )
        )
    )
}