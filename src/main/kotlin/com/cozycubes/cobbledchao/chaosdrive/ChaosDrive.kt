package com.cozycubes.cobbledchao.chaosdrive

import com.cozycubes.cobbledchao.chao.ChaoEntity
import com.cozycubes.cobbledchao.chao.ChaoStat
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class ChaosDrive(private val stats: Map<ChaoStat.Companion.STATS, Int>) : Item(
    Properties()
) {
    override fun interactLivingEntity(
        itemStack: ItemStack, player: Player, livingEntity: LivingEntity, interactionHand: InteractionHand
    ): InteractionResult {
        if (livingEntity !is ChaoEntity || player.level().isClientSide) {
            return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand)
        }

        if (livingEntity.isOnExpCooldown) return super.interactLivingEntity(
            itemStack,
            player,
            livingEntity,
            interactionHand
        )

        val chaoStats = livingEntity.chaoStats

        // TODO: Play star ball on top of head for/if any leveled up stats.
        val results = stats.map { (stat, value) -> chaoStats.boostStat(stat, value) }
        println("Client: ${player.level().isClientSide}, Result: ${results.all { it.failed }}, Level: ${livingEntity.chaoData.stats.fly.level}")
        if (results.all { it.failed }) {
            return InteractionResult.PASS
        }

        livingEntity.usedChaoDrive()
        itemStack.consume(1, player)

        return InteractionResult.CONSUME
    }
}