/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.client.chao

import com.cozycubes.cobbledchao.CobbledChao.modResource
import com.cozycubes.cobbledchao.chao.ChaoEntity
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.model.DefaultedEntityGeoModel

class ChaoModel : DefaultedEntityGeoModel<ChaoEntity>(MODEL) {
    companion object {
        val MODEL = modResource("chao/baby/chao_baby")
        val TEXTURE: ResourceLocation = modResource("textures/entity/chao/baby/chao_baby.png")
    }

    override fun getTextureResource(animatable: ChaoEntity): ResourceLocation {
        return TEXTURE
    }
}