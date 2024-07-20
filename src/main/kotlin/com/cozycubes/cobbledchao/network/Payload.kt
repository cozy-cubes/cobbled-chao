package com.cozycubes.cobbledchao.network

import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload

abstract class Payload : CustomPacketPayload {
    abstract fun codec(): StreamCodec<RegistryFriendlyByteBuf, ParticlePayload>
}