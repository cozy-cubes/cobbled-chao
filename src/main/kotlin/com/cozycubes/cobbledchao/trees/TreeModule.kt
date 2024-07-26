@file:Suppress("MemberVisibilityCanBePrivate")

package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.base.AbstractModule
import com.cozycubes.cobbledchao.base.BaseRegistry.registerBlock
import com.cozycubes.cobbledchao.base.BaseRegistry.registerItem
import com.cozycubes.cobbledchao.chao.ChaoStat
import com.cozycubes.cobbledchao.chaosdrive.ChaosDrive
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction

object TreeModule : AbstractModule() {
    val CHAO_TREE_SEED = registerBlock(
        SeedBlock(
            BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak()
                .sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)
        ), "chao_tree_seed"
    )
    val CHAO_TREE_TRUNK = registerBlock(
        TrunkBlock(
            BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.WOOD).noOcclusion()
                .isViewBlocking { _, _, _ -> false }.dynamicShape()
        ), "chao_tree_trunk", skipItem = true
    )
    val CHAO_TREE_FRUIT_BLOCK = registerBlock(
        FruitBlock(
            BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().strength(0.2f, 3.0f)
                .sound(SoundType.WOOD)
                .noOcclusion().pushReaction(PushReaction.DESTROY).isViewBlocking { _, _, _ -> false }.dynamicShape()
        ), "chao_tree_fruit_block", skipItem = true
    )
    val CHAO_TREE_LEAVES_BLOCK = registerBlock(
        LeafBlock(
            BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES)
        ), "chao_tree_leaves", skipItem = true
    )
    val CHAO_TREE_FRUIT = registerItem(
        ChaosDrive(
            mapOf(
                ChaoStat.Companion.STATS.STAMINA to 40
            )
        ), "chao_tree_fruit"
    )
    val CHAO_TREE_SAPLING = registerBlock(SaplingBlock(
        TreeGrowthStages.gardenTreeGrowthStages,
        BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().sound(SoundType.WOOD).noOcclusion()
            .isViewBlocking { _, _, _ -> false }
    ), "chao_tree_sapling", skipItem = true) as SaplingBlock

    override fun init() {}
}