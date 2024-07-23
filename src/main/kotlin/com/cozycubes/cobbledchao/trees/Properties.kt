package com.cozycubes.cobbledchao.trees

import net.minecraft.world.level.block.state.properties.BooleanProperty

object Properties {
    val U_CONNECT: BooleanProperty = BooleanProperty.create("up")
    val D_CONNECT: BooleanProperty = BooleanProperty.create("down")
    val N_CONNECT: BooleanProperty = BooleanProperty.create("north")
    val E_CONNECT: BooleanProperty = BooleanProperty.create("east")
    val S_CONNECT: BooleanProperty = BooleanProperty.create("south")
    val W_CONNECT: BooleanProperty = BooleanProperty.create("west")
}