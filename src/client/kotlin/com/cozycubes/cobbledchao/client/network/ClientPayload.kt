package com.cozycubes.cobbledchao.client.network

import com.cozycubes.cobbledchao.network.Payload
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

abstract class ClientPayload<T : Payload> {
    abstract fun receiveMessage(context: ClientPlayNetworking.Context)
}