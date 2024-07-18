package com.cozycubes.cobbledchao.chaosdrive

import com.cozycubes.cobbledchao.CobbledChao.modResource
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item

object ChaosDriveModule {
    val GREEN_DRIVE = Item(Item.Properties())
    val RED_DRIVE = Item(Item.Properties())
    val PURPLE_DRIVE = Item(Item.Properties())
    val YELLOW_DRIVE = Item(Item.Properties())

    fun registerAll() {
        Registry.register(BuiltInRegistries.ITEM, modResource("green_drive"), GREEN_DRIVE)
        Registry.register(BuiltInRegistries.ITEM, modResource("red_drive"), RED_DRIVE)
        Registry.register(BuiltInRegistries.ITEM, modResource("purple_drive"), PURPLE_DRIVE)
        Registry.register(BuiltInRegistries.ITEM, modResource("yellow_drive"), YELLOW_DRIVE)
    }
}