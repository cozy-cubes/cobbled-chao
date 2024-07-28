package com.cozycubes.cobbledchao.trees

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty

class GrowthStageEntry(
    val pos: BlockPos,
    val block: String,
    val switches: Map<String, Boolean> = emptyMap(),
    val counts: Map<String, Int> = emptyMap(),
    val facing: Direction? = null
) {
    fun evaluate(): BlockState {
        var retval = BuiltInRegistries.BLOCK[ResourceLocation.parse("cobbled-chao:$block")].defaultBlockState()

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

object TreeGrowthStages {
    val gardenTreeGrowthStages = listOf(
        listOf(
            GrowthStageEntry(
                BlockPos(0, 1, 0),
                "chaotree_garden_trunk",
                mapOf(
                    "up" to true,
                    "down" to true
                )
            ),
            GrowthStageEntry(
                BlockPos(0, 2, 0),
                "chaotree_garden_trunk",
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
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(-1, 2, 0),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(0, 2, 1),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(0, 2, -1),
                "chaotree_garden_leaves"
            ),
        ),
        listOf(
            GrowthStageEntry(
                BlockPos(0, 1, 0),
                "chaotree_garden_trunk",
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
                "chaotree_garden_trunk",
                mapOf(
                    "up" to true,
                    "down" to true,
                ),
            ),
            GrowthStageEntry(
                BlockPos(0, 3, 0),
                "chaotree_garden_trunk",
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
                "air"
            ),
            GrowthStageEntry(
                BlockPos(-1, 2, 0),
                "air"
            ),
            GrowthStageEntry(
                BlockPos(0, 2, 1),
                "air"
            ),
            GrowthStageEntry(
                BlockPos(0, 2, -1),
                "air"
            ),

            GrowthStageEntry(
                BlockPos(1, 3, 0),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(-1, 3, 0),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(0, 3, 1),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(0, 3, -1),
                "chaotree_garden_leaves"
            ),
        ),
        listOf(
            GrowthStageEntry(
                BlockPos(0, 1, 0),
                "chaotree_garden_trunk",
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
                "chaotree_garden_trunk",
                mapOf(
                    "up" to true,
                    "down" to true
                ),
            ),
            GrowthStageEntry(
                BlockPos(0, 3, 0),
                "chaotree_garden_trunk",
                mapOf(
                    "up" to true,
                    "down" to true
                ),
            ),
            GrowthStageEntry(
                BlockPos(0, 4, 0),
                "chaotree_garden_trunk",
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
                "air"
            ),
            GrowthStageEntry(
                BlockPos(-1, 3, 0),
                "air"
            ),
            GrowthStageEntry(
                BlockPos(0, 3, 1),
                "air"
            ),
            GrowthStageEntry(
                BlockPos(0, 3, -1),
                "air"
            ),

            GrowthStageEntry(
                BlockPos(1, 4, 0),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(-1, 4, 0),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(0, 4, 1),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(0, 4, -1),
                "chaotree_garden_leaves"
            ),

            GrowthStageEntry(
                BlockPos(2, 5, 0),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(-2, 3, 0),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(0, 3, 2),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(0, 3, -2),
                "chaotree_garden_leaves"
            ),
        ),
        listOf(
            GrowthStageEntry(
                BlockPos(0, 4, 0),
                "chaotree_garden_trunk",
                mapOf(
                    "up" to true,
                    "down" to true,
                )
            ),
            GrowthStageEntry(
                BlockPos(0, 5, 0),
                "chaotree_garden_trunk",
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
                "air"
            ),
            GrowthStageEntry(
                BlockPos(-1, 4, 0),
                "air"
            ),
            GrowthStageEntry(
                BlockPos(0, 4, 1),
                "air"
            ),
            GrowthStageEntry(
                BlockPos(0, 4, -1),
                "air"
            ),

            GrowthStageEntry(
                BlockPos(2, 5, 0),
                "air"
            ),
            GrowthStageEntry(
                BlockPos(-2, 3, 0),
                "air"
            ),
            GrowthStageEntry(
                BlockPos(0, 3, 2),
                "air"
            ),
            GrowthStageEntry(
                BlockPos(0, 3, -2),
                "air"
            ),

            GrowthStageEntry(
                BlockPos(1, 5, 0),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(-1, 5, 0),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(0, 5, 1),
                "chaotree_garden_leaves"
            ),
            GrowthStageEntry(
                BlockPos(0, 5, -1),
                "chaotree_garden_leaves"
            ),

            GrowthStageEntry(
                BlockPos(2, 6, 0),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(-2, 4, 0),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(0, 4, 2),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(0, 4, -2),
                "chaotree_garden_leaves",
            ),
        ),
        listOf(
            GrowthStageEntry(
                BlockPos(0, 5, 0),
                "chaotree_garden_trunk",
                mapOf(
                    "up" to true,
                    "down" to true,
                ),
            ),
            GrowthStageEntry(
                BlockPos(0, 6, 0),
                "chaotree_garden_trunk",
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
                "air"
            ),
            GrowthStageEntry(
                BlockPos(-2, 4, 0),
                "air"
            ),
            GrowthStageEntry(
                BlockPos(0, 4, 2),
                "air"
            ),
            GrowthStageEntry(
                BlockPos(0, 4, -2),
                "air"
            ),

            GrowthStageEntry(
                BlockPos(1, 6, 0),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(-1, 6, 0),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(0, 6, 1),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(0, 6, -1),
                "chaotree_garden_leaves",
            ),

            GrowthStageEntry(
                BlockPos(2, 6, 0),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(-2, 6, 0),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(0, 6, 2),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(0, 6, -2),
                "chaotree_garden_leaves",
            ),

            GrowthStageEntry(
                BlockPos(3, 7, 0),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(-3, 5, 0),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(0, 5, 3),
                "chaotree_garden_leaves",
            ),
            GrowthStageEntry(
                BlockPos(0, 5, -3),
                "chaotree_garden_leaves",
            ),

            GrowthStageEntry(
                BlockPos(1, 5, 0),
                "chaotree_garden_fruit",
                facing = Direction.EAST
            ),
            GrowthStageEntry(
                BlockPos(-1, 5, 0),
                "chaotree_garden_fruit",
                facing = Direction.WEST
            ),
            GrowthStageEntry(
                BlockPos(0, 5, 1),
                "chaotree_garden_fruit",
                facing = Direction.SOUTH
            ),
            GrowthStageEntry(
                BlockPos(0, 5, -1),
                "chaotree_garden_fruit",
                facing = Direction.NORTH
            ),
        )
    )
}