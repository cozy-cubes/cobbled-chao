package com.cozycubes.cobbledchao.chaosdrive

import com.cozycubes.cobbledchao.CobbledChao.modResource
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

enum class ChaosDriveTags(val tag: TagKey<Item>) {
    CHAOS_DRIVES(TagKey.create(Registries.ITEM, modResource("chaos_drives")))
}