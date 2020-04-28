package net.defracted.adminutils.listeners

import net.defracted.adminutils.Main
import net.defracted.adminutils.util.Formatters
import net.defracted.adminutils.util.Formatters.chat

import org.bukkit.BanList
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent

import java.util.*


class ConnectionListener(private val plugin: Main) : Listener {
    private val appealUrl = plugin.config.getString("appeal_url")

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

                message += chat("&7Причина: &f$reason\n&7Разбан: &b&n${appealUrl}")

                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message)
                return
            }
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        if (player.hasPermission("adminutils.manage")) {
            Bukkit.getOnlinePlayers().forEach { p -> p.playSound(p.location, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1f, 1f) }
            event.joinMessage = Formatters.chat("&c&l${player.displayName} &bприсоединился к игре.")
        }
    }
}