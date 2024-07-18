package com.cozycubes.cobbledchao.client

import com.cozycubes.cobbledchao.client.chao.ClientChaoModule
import net.fabricmc.api.ClientModInitializer

object CobbledChaoClient : ClientModInitializer {
	override fun onInitializeClient() {
		ClientChaoModule.registerAll()
	}
}