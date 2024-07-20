package com.cozycubes.cobbledchao.network

import com.cozycubes.cobbledchao.CobbledChao
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload

open class ParticlePayload(
    val particleType: SimpleParticleType,
    val x: Double,
    val y: Double,
    val z: Double,
    val speedX: Double,
    val speedY: Double,
    val speedZ: Double
) : Payload() {
    companion object {
        val TYPE = CustomPacketPayload.Type<ParticlePayload>(CobbledChao.modResource("add_particle"))
        val CODEC: StreamCodec<RegistryFriendlyByteBuf, ParticlePayload> =
            StreamCodec.of({ buf: RegistryFriendlyByteBuf, packet: ParticlePayload ->
                ParticleTypes.STREAM_CODEC.encode(buf, packet.particleType)
                buf.writeDouble(packet.x)
                buf.writeDouble(packet.y)
                buf.writeDouble(packet.z)
                buf.writeDouble(packet.speedX)
                buf.writeDouble(packet.speedY)
                buf.writeDouble(packet.speedZ)
            }, { buf: RegistryFriendlyByteBuf ->
                ParticlePayload(
                    ParticleTypes.STREAM_CODEC.decode(buf).type as SimpleParticleType,
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readDouble(),
                )
            })
    }

    override fun type(): CustomPacketPayload.Type<out ParticlePayload> {
        return TYPE
    }

    override fun codec(): StreamCodec<RegistryFriendlyByteBuf, ParticlePayload> {
        return CODEC
    }
}