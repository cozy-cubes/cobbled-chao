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
    val GARDEN_TREE = registerTree("garden")

    override fun init() {}

    fun registerTree(name: String): Tree {
        val prefix = "chaotree_$name"

        val sapling = registerBlock(
            SaplingBlock(TreeGrowthStages.gardenTreeGrowthStages,
                BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().sound(SoundType.WOOD)
                    .noOcclusion().isViewBlocking { _, _, _ -> false }), "${prefix}_sapling", skipItem = true
        ) as SaplingBlock
        val seed = registerBlock(
            SeedBlock(
                sapling,
                BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak()
                    .sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)
            ), "${prefix}_seed"
        ) as SeedBlock
        val trunk = registerBlock(
            TrunkBlock(
                BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.WOOD).noOcclusion()
                    .isViewBlocking { _, _, _ -> false }.dynamicShape()
            ), "${prefix}_trunk", skipItem = true
        ) as TrunkBlock
        val leaf = registerBlock(
            LeafBlock(
                BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES)
            ), "${prefix}_leaves", skipItem = true
        ) as LeafBlock
        val fruitItem = registerItem(
            ChaosDrive(
                mapOf(
                    ChaoStat.Companion.STATS.STAMINA to 40
                )
            ), "${prefix}_fruit"
        ) as ChaosDrive
        val fruitBlock = registerBlock(
            FruitBlock(
                fruitItem,
                BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().strength(0.2f, 3.0f)
                    .sound(SoundType.WOOD).noOcclusion().pushReaction(PushReaction.DESTROY)
                    .isViewBlocking { _, _, _ -> false }.dynamicShape()
            ), "${prefix}_fruit", skipItem = true
        ) as FruitBlock

        return Tree(
            seed, sapling, trunk, leaf, fruitBlock, fruitItem
        )
    }
}

