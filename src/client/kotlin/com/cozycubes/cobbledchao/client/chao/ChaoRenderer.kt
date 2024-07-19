package com.cozycubes.cobbledchao.client.chao

import com.cozycubes.cobbledchao.chao.ChaoEntity
import net.minecraft.client.renderer.entity.EntityRendererProvider
import software.bernie.geckolib.renderer.specialty.DynamicGeoEntityRenderer

class ChaoRenderer(renderManager: EntityRendererProvider.Context) :
    DynamicGeoEntityRenderer<ChaoEntity>(renderManager, ChaoModel())
