package net.defracted.adminutils.listeners

import net.defracted.adminutils.Main
import net.defracted.adminutils.util.Formatters
import net.defracted.adminutils.util.Other
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!
// TODO: MUST HANDLE MUTES!!!

class ChatListener(private val plugin: Main) : Listener {
    @EventHandler
    fun onNewMessage(event: AsyncPlayerChatEvent) {
        val player: Player = event.player

        if (plugin.muteManager.mutedPlayers.contains(player.uniqueId) && !player.hasPermission("adminutils.mute.bypass")) {
            val info = plugin.muteManager.mutedPlayers.getValue(player.uniqueId)

            if (System.currentTimeMillis() <= info.expiresAt) {
                val until = Other.timeDiff(info.expiresAt, System.currentTimeMillis())

                player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f)
                player.sendMessage(Formatters.chat("&c&m————————————————————————————\n&cВам запрещено писать в чат!"))
                player.sendMessage(Formatters.chat("\n&cПричина: &f${info.reason}\n&cЗапретил: &f${info.moderator}"))
                player.sendMessage(Formatters.chat("\n&cДо снятия мута осталось: &f${until["days"]} дн. ${until["hours"]} ч. ${until["minutes"]} мин. ${until["seconds"]} сек."))
                player.sendMessage(Formatters.chat("&c&m————————————————————————————"))

                return
            } else {
                plugin.muteManager.delMute(player.uniqueId)
            }
        }
    }
}