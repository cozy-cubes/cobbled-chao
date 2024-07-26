package com.cozycubes.cobbledchao.base

import com.cozycubes.cobbledchao.CobbledChao
import com.cozycubes.cobbledchao.CobbledChao.modResource
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EntityType.EntityFactory
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object BaseRegistry {
    fun registerBlock(block: Block, name: String, skipItem: Boolean = false, itemProps: Item.Properties = Item.Properties()): Block {
        if (!skipItem) Registry.register(BuiltInRegistries.ITEM, modResource(name), BlockItem(block, itemProps))
        return Registry.register(BuiltInRegistries.BLOCK, modResource(name), block)
    }

    fun registerItem(item: Item, name: String): Item {
        return Registry.register(BuiltInRegistries.ITEM, modResource(name), item)
    }

    fun <T : PathfinderMob> registerEntityType(
        name: String,
        entity: EntityFactory<T>,
        width: Float,
        height: Float,
    ): EntityType<T> {
        return Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            modResource(name),
            EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name)
        )
    }
}