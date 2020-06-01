package net.defracted.adminutils.listeners

import net.defracted.adminutils.Main
import net.defracted.adminutils.util.Formatters
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatSender(private val plugin: Main) : Listener {
    private val shouldUseVault: Boolean = plugin.config.getBoolean("use_vault")

    @EventHandler
    fun onAnotherNewMessage(event: AsyncPlayerChatEvent) {
        event.isCancelled = true

        val player: Player = event.player

        var msg = event.message
        var prefix = ""

        if (player.isOp || player.hasPermission("adminutils.colored_chat")) msg = Formatters.chat(event.message)

        if (!shouldUseVault) {
            if (player.isOp) {
                prefix = "&c&lADMIN &r"
            } else if (player.hasPermission("adminutils.prefix.senior")) {
                prefix = "&6&lSR MOD &r"
            } else if (player.hasPermission("adminutils.prefix.mod")) {
                prefix = "&2&lMOD &r"
            } else if (player.hasPermission("adminutils.prefix.helper")) {
                prefix = "&9&lHELPER &r"
            }
        } else {
            if (plugin.chat != null) {
                val vaultPrefix = plugin.chat!!.getPlayerPrefix(player.world.name, player)

                if (vaultPrefix != null) {
                    prefix = vaultPrefix
                }
            }
        }

        Bukkit.getServer().broadcastMessage(Formatters.chat("$prefix${player.name}: ") + msg)
    }

}