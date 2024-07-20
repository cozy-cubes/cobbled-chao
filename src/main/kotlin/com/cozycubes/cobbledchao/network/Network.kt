package com.cozycubes.cobbledchao.network

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.level.ServerPlayer

object Network {
    fun sendAddParticlePacket(player: ServerPlayer, packet: ParticlePayload) {
        ServerPlayNetworking.send(player, packet)
    }
}

