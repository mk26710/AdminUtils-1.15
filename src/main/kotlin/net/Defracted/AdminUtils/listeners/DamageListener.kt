package net.Defracted.AdminUtils.listeners

import net.Defracted.AdminUtils.Main
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

import org.bukkit.event.entity.EntityDamageEvent


class DamageListener(private val plugin: Main) : Listener {
    @EventHandler
    fun onHit(event: EntityDamageEvent) {
        if (event.entity is Player) {
            val player = event.entity as Player
            if (plugin.playersInGodMode.contains(player.uniqueId)) {
                event.isCancelled = true
            }
        }
    }

}