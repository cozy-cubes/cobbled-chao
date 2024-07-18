/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.chao

class ChaoStat(val stat: STATS, val value: Int) {
    companion object {
        enum class STATS {
            SWIM, FLY, RUN, POWER, STAMINA, INTELLIGENCE, LUCK
        }
    }
}