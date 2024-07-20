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
        if (livingEntity !is ChaoEntity) {
            return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand)
        }

        if (livingEntity.isOnStatCooldown) return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand)

        val chaoStats = livingEntity.chaoStats

        // TODO: Play star ball on top of head for/if any leveled up stats.
        // TODO: Expand on this because I want to figure out if any leveled up, but also if any didn't receive exp because max level.
        val leveledUp =
            stats.mapNotNull { (stat, value) -> if (chaoStats.boostStat(stat, value)) stat else null }.distinct()

        livingEntity.usedChaoDrive()
        itemStack.consume(1, player)

        // TODO: Make sure this is the right thing to return...
        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand)
    }
}