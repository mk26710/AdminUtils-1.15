package net.Defracted.AdminUtils.listeners

import net.Defracted.AdminUtils.Main
import net.Defracted.AdminUtils.util.Formatters.chat
import org.bukkit.BanList
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.util.*


class ConnectionListener(private val plugin: Main) : Listener {
    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerLogin(event: AsyncPlayerPreLoginEvent?) {
        if (event == null) return
        val name = event.name
        val addr = event.address


        // Ban IP
        if (Bukkit.getBanList(BanList.Type.IP).isBanned(addr.hostAddress)) {
            val entry = Bukkit.getBanList(BanList.Type.IP).getBanEntry(addr.hostAddress) ?: return

            val reason = entry.reason
            val message = chat("&cВаш IP-адрес забанен!\n\n&7Причина: &f$reason")
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message)
            return
        }

        // Ban Player
        if (Bukkit.getBanList(BanList.Type.NAME).isBanned(name)) {
            val entry = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(name) ?: return
            val reason = entry.reason
            val expiration: Date? = entry.expiration
            val punishmentDurationStr: String?
            punishmentDurationStr = expiration?.toString()

            if (reason != null) {
                var message: String = if (expiration == null) {
                    chat("&cВы забанены навсегда!\n\n")
                } else {
                    chat("&cВы временно забанены!\n\n&7Бан действует до: &f$punishmentDurationStr\n")
                }

                message += chat("&7Причина: &f$reason\n&7Разбан: &b&n${plugin.config.getString("appeal_url")}")
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message)
                return
            }
        }
    }
}