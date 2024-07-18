/*
 * Copyright (C) 2024 Cozy Cubes Studios
 *
 * This Source Code Form is subject to the terms of the MIT License.
 * If a copy of the MIT was not distributed with this
 * file, You can obtain one at https://opensource.org/licenses/MIT.
 */
package com.cozycubes.cobbledchao.client

import com.cozycubes.cobbledchao.client.chao.ClientChaoModule
import net.fabricmc.api.ClientModInitializer

object CobbledChaoClient : ClientModInitializer {
	override fun onInitializeClient() {
		ClientChaoModule.registerAll()
	}
}