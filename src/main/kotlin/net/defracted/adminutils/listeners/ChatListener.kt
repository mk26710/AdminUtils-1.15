package net.defracted.adminutils.listeners

import net.defracted.adminutils.Main
import net.defracted.adminutils.mutes.MutedInfo
import net.defracted.adminutils.util.Formatters
import net.luckperms.api.LuckPerms
import net.luckperms.api.query.QueryOptions
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.*

class ChatListener(private val plugin: Main) : Listener {
    private val useLp: Boolean = plugin.config.getBoolean("use_luckperms")

    private val provider = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)
    private val LuckPermsAPI = provider?.provider


    @EventHandler
    fun onNewMessage(event: AsyncPlayerChatEvent) {
        event.isCancelled = true

        val player: Player = event.player

        if (plugin.muteManager.mutedPlayers.contains(player.uniqueId)) {
            val info = plugin.muteManager.mutedPlayers.getValue(player.uniqueId)

            if (System.currentTimeMillis() <= info.expiresAt) {
                val until = Date(info.expiresAt).toString()

                player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f)
                player.sendMessage(Formatters.chat("&c&m————————————————————————————\n&cВам запрещено писать в чат!"))
                player.sendMessage(Formatters.chat("\n&cПричина: &f${info.reason}\n&cЗапретил: &f${info.moderator}"))
                player.sendMessage(Formatters.chat("\n&cМут действует до: &f$until\n&c&m————————————————————————————"))
                return
            } else {
                plugin.muteManager.delMute(player.uniqueId)
            }

        }

        var msg = event.message
        var prefix = ""

        if (player.isOp || player.hasPermission("adminutils.colored_chat")) msg = Formatters.chat(event.message)

        if (!useLp) {
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
            if (LuckPermsAPI != null) {
                val luckpermsPrefix = LuckPermsAPI.userManager.getUser(player.uniqueId)?.cachedData?.getMetaData(QueryOptions.nonContextual())?.prefix

                if (luckpermsPrefix != null) {
                    prefix = luckpermsPrefix
                }
            }
        }

        Bukkit.getServer().broadcastMessage(Formatters.chat("$prefix${player.name}: ") + msg)
    }
}