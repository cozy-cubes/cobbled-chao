package com.cozycubes.cobbledchao.client.chao

import com.cozycubes.cobbledchao.chao.ChaoModule
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry

object ClientChaoModule {
    fun registerAll() {
        EntityRendererRegistry.register(ChaoModule.CHAO, ::ChaoRenderer)
    }
}