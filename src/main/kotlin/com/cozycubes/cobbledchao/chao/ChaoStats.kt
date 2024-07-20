/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.chao

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder

class ChaoStats(
    val swim: ChaoStat = ChaoStat(),
    val fly: ChaoStat = ChaoStat(),
    val run: ChaoStat = ChaoStat(),
    val power: ChaoStat = ChaoStat(),
    val stamina: ChaoStat = ChaoStat(),
    val intelligence: ChaoStat = ChaoStat(),
    val luck: ChaoStat = ChaoStat()
) {
    companion object {
        val CODEC: Codec<ChaoStats> = RecordCodecBuilder.create { instance ->
            instance.group(
                ChaoStat.CODEC.fieldOf("swim").forGetter(ChaoStats::swim),
                ChaoStat.CODEC.fieldOf("fly").forGetter(ChaoStats::fly),
                ChaoStat.CODEC.fieldOf("run").forGetter(ChaoStats::run),
                ChaoStat.CODEC.fieldOf("power").forGetter(ChaoStats::power),
                ChaoStat.CODEC.fieldOf("stamina").forGetter(ChaoStats::stamina),
                ChaoStat.CODEC.fieldOf("intelligence").forGetter(ChaoStats::intelligence),
                ChaoStat.CODEC.fieldOf("luck").forGetter(ChaoStats::luck),
            ).apply(instance, ::ChaoStats)
        }
    }

    fun getStat(stat: ChaoStat.Companion.STATS): ChaoStat = when (stat) {
        ChaoStat.Companion.STATS.SWIM -> swim
        ChaoStat.Companion.STATS.FLY -> fly
        ChaoStat.Companion.STATS.RUN -> run
        ChaoStat.Companion.STATS.POWER -> power
        ChaoStat.Companion.STATS.STAMINA -> stamina
        ChaoStat.Companion.STATS.INTELLIGENCE -> intelligence
        ChaoStat.Companion.STATS.LUCK -> luck
    }

    fun grantStatExp(stat: ChaoStat.Companion.STATS, grantedExp: Int): BoostStatResult {
        return getStat(stat).grantStatExp(grantedExp)
    }
}
