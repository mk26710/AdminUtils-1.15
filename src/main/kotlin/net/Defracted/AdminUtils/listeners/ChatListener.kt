package net.Defracted.AdminUtils.listeners

import net.Defracted.AdminUtils.Main
import net.Defracted.AdminUtils.util.Formatters
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatListener(private val plugin: Main) : Listener {
    @EventHandler
    fun onNewMessage(event: AsyncPlayerChatEvent) {
        event.isCancelled = true

        val player: Player = event.player
        var msg = event.message
        var prefix = ""

        if (player.isOp) {
            prefix = "&c&lADMIN &r"
            msg = Formatters.chat(event.message)
        }

        Bukkit.getServer().broadcastMessage(Formatters.chat("$prefix${player.name}: ") + msg)
    }
}