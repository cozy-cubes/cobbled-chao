package com.cozycubes.cobbledchao.chao

class ChaoStat(val stat: STATS, val value: Int) {
    companion object {
        enum class STATS {
            SWIM, FLY, RUN, POWER, STAMINA, INTELLIGENCE, LUCK
        }
    }
}