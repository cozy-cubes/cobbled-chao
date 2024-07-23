@file:Suppress("MemberVisibilityCanBePrivate")

package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.CobbledChao.modResource
import com.cozycubes.cobbledchao.chao.ChaoStat
import com.cozycubes.cobbledchao.chaosdrive.ChaosDrive
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LeavesBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction

object TreeModule {
    val CHAO_TREE_SEED = SeedBlock(
        BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak()
            .sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)
    )
    val CHAO_TREE_SAPLING = SaplingBlock(
        BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().sound(SoundType.WOOD).noOcclusion().isViewBlocking { _, _, _ -> false }
    )
    val CHAO_TREE_TRUNK = TrunkBlock(
        BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.WOOD).noOcclusion().isViewBlocking { _, _, _ -> false }.dynamicShape()
    )
    val CHAO_TREE_FRUIT_BLOCK = FruitBlock(
        BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().strength(0.2f, 3.0f).sound(SoundType.WOOD)
            .noOcclusion().pushReaction(PushReaction.DESTROY).isViewBlocking { _, _, _ -> false }.dynamicShape()
    )
    val CHAO_TREE_LEAVES_BLOCK = LeafBlock(
        BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES)
    )
    val CHAO_TREE_FRUIT = ChaosDrive(
        mapOf(
            ChaoStat.Companion.STATS.STAMINA to 40
        )
    )

    fun registerAll() {
        registerBlock(CHAO_TREE_SEED, "chao_tree_seed")
        registerBlock(CHAO_TREE_SAPLING, "chao_tree_sapling", noItem = true)
        registerBlock(CHAO_TREE_TRUNK, "chao_tree_trunk", noItem = true)
        registerBlock(CHAO_TREE_FRUIT_BLOCK, "chao_tree_fruit_block", noItem = true)
        registerBlock(CHAO_TREE_LEAVES_BLOCK, "chao_tree_leaves", noItem = true)

        registerItem(CHAO_TREE_FRUIT, "chao_tree_fruit")
    }

    fun registerBlock(
        block: Block, name: String, noItem: Boolean = false, itemProperties: Item.Properties = Item.Properties()
    ) {
        Registry.register(BuiltInRegistries.BLOCK, modResource(name), block)
        if (!noItem) {
            Registry.register(BuiltInRegistries.ITEM, modResource(name), BlockItem(block, itemProperties))
        }
    }

    fun registerItem(item: Item, name: String) {
        Registry.register(BuiltInRegistries.ITEM, modResource(name), item)
    }
}