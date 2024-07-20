package com.cozycubes.cobbledchao.chao

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder

class ChaoData(
    val stats: ChaoStats = ChaoStats(),
    var expCooldown: Int = 0
) {
    companion object {
        val CODEC: Codec<ChaoData> = RecordCodecBuilder.create { instance ->
            instance.group(
                ChaoStats.CODEC.fieldOf("stats").forGetter(ChaoData::stats),
                Codec.INT.fieldOf("expCooldown").forGetter(ChaoData::expCooldown)
            ).apply(instance, ::ChaoData)
        }
    }
}