package com.cozycubes.cobbledchao.trees

import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.Property

object Properties {
    val propsByName = mutableMapOf<String, Property<*>>()

    val U_CONNECT: BooleanProperty = register(BooleanProperty.create("up"))
    val D_CONNECT: BooleanProperty = register(BooleanProperty.create("down"))
    val N_CONNECT: BooleanProperty = register(BooleanProperty.create("north"))
    val E_CONNECT: BooleanProperty = register(BooleanProperty.create("east"))
    val S_CONNECT: BooleanProperty = register(BooleanProperty.create("south"))
    val W_CONNECT: BooleanProperty = register(BooleanProperty.create("west"))
    val MARKED: BooleanProperty = register(BooleanProperty.create("marked"))
    val SIZE: IntegerProperty = register(IntegerProperty.create("size", 0, 2))

    fun <R, T : Property<R>> register(prop: T): T {
        propsByName[prop.getName()] = prop
        return prop
    }
}