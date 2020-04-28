package net.defracted.adminutils.listeners

import net.defracted.adminutils.Main
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent


class DamageListener(private val plugin: Main) : Listener {
    @EventHandler
    fun onHit(event: EntityDamageEvent) {
        if (event.entity is Player) {
            val player = event.entity as Player
            if (plugin.godModePlayers.contains(player.uniqueId)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player: Player = event.entity

        if (player.hasPermission("adminutils.back")) {
            plugin.lastDeathsLocations[player.uniqueId] = player.location
        }
    }
}