package com.cozycubes.cobbledchao.chao

import com.cozycubes.cobbledchao.data.NbtKeys
import com.cozycubes.cobbledchao.extensions.possibleInt
import net.minecraft.nbt.CompoundTag

class ChaoData(
    swimValue: Int,
    flyValue: Int,
    runValue: Int,
    powerValue: Int,
    staminaValue: Int,
    intelligenceValue: Int,
    luckValue: Int
) {
    val swim: ChaoStat = ChaoStat(ChaoStat.Companion.STATS.SWIM, swimValue)
    val fly: ChaoStat = ChaoStat(ChaoStat.Companion.STATS.FLY, flyValue)
    val run: ChaoStat = ChaoStat(ChaoStat.Companion.STATS.RUN, runValue)
    val power: ChaoStat = ChaoStat(ChaoStat.Companion.STATS.POWER, powerValue)
    val stamina: ChaoStat = ChaoStat(ChaoStat.Companion.STATS.STAMINA, staminaValue)
    val intelligence: ChaoStat = ChaoStat(ChaoStat.Companion.STATS.INTELLIGENCE, intelligenceValue)
    val luck: ChaoStat = ChaoStat(ChaoStat.Companion.STATS.LUCK, luckValue)

    companion object {
        fun fromNbt(nbt: CompoundTag): ChaoData {
            val swim = nbt.possibleInt(NbtKeys.SWIM.nbtKey)
            val fly = nbt.possibleInt(NbtKeys.FLY.nbtKey)
            val run = nbt.possibleInt(NbtKeys.RUN.nbtKey)
            val power = nbt.possibleInt(NbtKeys.POWER.nbtKey)
            val stamina = nbt.possibleInt(NbtKeys.STAMINA.nbtKey)
            val intelligence = nbt.possibleInt(NbtKeys.INTELLIGENCE.nbtKey)
            val luck = nbt.possibleInt(NbtKeys.LUCK.nbtKey)

            return ChaoData(
                swim, fly, run, power, stamina, intelligence, luck
            )
        }

        fun generateZeroes(): ChaoData {
            return ChaoData(0, 0, 0, 0, 0, 0, 0)
        }
    }

    fun toNbt(): CompoundTag {
        val nbt = CompoundTag()

        nbt.putInt(NbtKeys.SWIM.nbtKey, swim.value)
        nbt.putInt(NbtKeys.FLY.nbtKey, fly.value)
        nbt.putInt(NbtKeys.RUN.nbtKey, run.value)
        nbt.putInt(NbtKeys.POWER.nbtKey, power.value)
        nbt.putInt(NbtKeys.STAMINA.nbtKey, stamina.value)
        nbt.putInt(NbtKeys.INTELLIGENCE.nbtKey, intelligence.value)
        nbt.putInt(NbtKeys.LUCK.nbtKey, luck.value)

        return nbt
    }
}