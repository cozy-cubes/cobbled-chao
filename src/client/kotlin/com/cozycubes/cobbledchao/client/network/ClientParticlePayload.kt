package com.cozycubes.cobbledchao.client.network

import com.cozycubes.cobbledchao.network.ParticlePayload
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

class ClientParticlePayload(
    private val particlePayload: ParticlePayload
) : ClientPayload<ParticlePayload>() {
    override fun receiveMessage(context: ClientPlayNetworking.Context) {
        context.client().level?.addParticle(
            particlePayload.particleType,
            particlePayload.x,
            particlePayload.y,
            particlePayload.z,
            particlePayload.speedX,
            particlePayload.speedY,
            particlePayload.speedZ
        )
    }
}