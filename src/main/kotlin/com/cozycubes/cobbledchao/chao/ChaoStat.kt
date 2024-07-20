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
import kotlin.math.floor

class ChaoStat(
    val grade: Int = 0,
    var experience: Int = 0,
    var level: Int = 0
) {
    companion object {
        // TODO: Abstract and datapackable this.
        enum class STATS(val color: Int?) {
            SWIM(0xfeff40),
            FLY(0x9548ce),
            RUN(0x8bff3c),
            POWER(0xdf412b),
            STAMINA(0x93f1f1),
            INTELLIGENCE(null),
            LUCK(null)
        }

        const val MAX_LEVEL: Int = 99
        const val MAX_EXP: Double = 100.0

        val CODEC: Codec<ChaoStat> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.INT.fieldOf("grade").forGetter(ChaoStat::grade),
                Codec.INT.fieldOf("experience").forGetter(ChaoStat::experience),
                Codec.INT.fieldOf("level").forGetter(ChaoStat::level)
            ).apply(instance, ::ChaoStat)
        }
    }

    fun grantStatExp(givenExp: Int): BoostStatResult {
        if (level >= MAX_LEVEL) return BoostStatResult(failed = true)
        val newExperience = experience + givenExp
        experience = (newExperience % MAX_EXP).toInt()
        val levelBoost = floor(newExperience / MAX_EXP).toInt()
        level += levelBoost

        return BoostStatResult(
            leveledUp = levelBoost > 0
        )
    }
}