package com.cozycubes.cobbledchao

import com.cozycubes.cobbledchao.chao.ChaoModule
import com.cozycubes.cobbledchao.chaosdrive.ChaosDriveModule
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory

object CobbledChao : ModInitializer {
    val MOD_ID = "cobbled-chao"
    private val logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        ChaoModule.registerAll()
        ChaosDriveModule.registerAll()
    }

    fun modResource(name: String): ResourceLocation {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name)
    }
}