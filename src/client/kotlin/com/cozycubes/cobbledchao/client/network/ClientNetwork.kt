package com.cozycubes.cobbledchao.client.network

import com.cozycubes.cobbledchao.network.ParticlePayload
import com.cozycubes.cobbledchao.network.Payload
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import kotlin.reflect.KFunction1

object ClientNetwork {
    fun init() {
        registerPayloadType<ParticlePayload>(ParticlePayload.TYPE, ParticlePayload.CODEC, ::ClientParticlePayload)
    }

    private fun <T : Payload> registerPayloadType(
        type: CustomPacketPayload.Type<ParticlePayload>,
        codec: StreamCodec<RegistryFriendlyByteBuf, ParticlePayload>,
        wrapper: KFunction1<ParticlePayload, ClientParticlePayload>
    ) {
        PayloadTypeRegistry.playS2C().register(type, codec)
        ClientPlayNetworking.registerGlobalReceiver(type) { packet, context ->
            context.client().execute { wrapper(packet).receiveMessage(context) }
        }
    }
}