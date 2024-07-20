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

        val chaoStats = livingEntity.chaoStats

        // TODO: Play star ball on top of head for/if any leveled up stats.
        val leveledUp =
            stats.mapNotNull { (stat, value) -> if (chaoStats.boostStat(stat, value)) stat else null }.distinct()

        livingEntity.statExpCooldown = 90

        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand)
    }
}