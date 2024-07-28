package com.cozycubes.cobbledchao.trees

import com.cozycubes.cobbledchao.chaosdrive.ChaosDrive

data class Tree(
    val seed: SeedBlock,
    val sapling: SaplingBlock,
    val trunk: TrunkBlock,
    val leaves: LeafBlock,
    val fruit: FruitBlock,
    val fruitItem: ChaosDrive
)