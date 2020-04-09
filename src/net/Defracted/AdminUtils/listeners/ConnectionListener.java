package net.Defracted.AdminUtils.listeners;

import net.Defracted.AdminUtils.Main;
import net.Defracted.AdminUtils.util.Formatters;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.net.InetAddress;
import java.util.Date;

public class ConnectionListener implements Listener {
    private Main plugin;

    public ConnectionListener(Main instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        if (event == null) return;

        String name = event.getName();
        InetAddress addr = event.getAddress();


        // Ban IP
        if (Bukkit.getBanList(BanList.Type.IP).isBanned(addr.getHostAddress())) {
            BanEntry entry = Bukkit.getBanList(BanList.Type.IP).getBanEntry(addr.getHostAddress());

            if (entry == null) {
                return;
            }

            String reason = entry.getReason();
            String message = Formatters.chat("&cВаш IP-адрес забанен!\n\n&7Причина: &f" + reason);
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message);
            return;
        }

        // Ban Player
        if (Bukkit.getBanList(BanList.Type.NAME).isBanned(name)) {
            BanEntry entry = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(name);

            if (entry == null) {
                return;
            }

            String reason = entry.getReason();
            Date expiration = entry.getExpiration();

            String punishmentDurationStr;
            if (expiration == null) {
                punishmentDurationStr = null;
            } else {
                punishmentDurationStr = expiration.toString();
            }

            if (reason != null) {
                String message;

                if (expiration == null) {
                    message = Formatters.chat("&cВы забанены навсегда!\n\n");
                } else {
                    message = Formatters.chat("&cВы временно забанены!\n\n&7Бан действует до: &f" + punishmentDurationStr + "\n");
                }

                message += Formatters.chat("&7Причина: &f" + reason + "\n\n&7Разбан: &b&n" + plugin.getConfig().getString("appeal_url"));

                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message);
                return;
            }
        }

    }
}
