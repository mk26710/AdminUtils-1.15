package net.Defracted.AdminUtils;

import net.Defracted.AdminUtils.util.Formatters;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.net.InetAddress;
import java.util.Date;

public class MainListener implements Listener {
    static Main plugin;

    public MainListener(Main instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        String name = event.getName();
        InetAddress addr = event.getAddress();

        try {
            // Ban IP
            if (Bukkit.getBanList(BanList.Type.IP).isBanned(addr.getHostAddress())) {
                String reason = Bukkit.getBanList(BanList.Type.IP).getBanEntry(addr.getHostAddress()).getReason();
                String message = Formatters.chat("&cВаш IP-адрес забанен!\n\n&7Причина: &f" + reason);
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message);
            }

            // Ban Player
            if (Bukkit.getBanList(BanList.Type.NAME).isBanned(name)) {
                String reason = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(name).getReason();
                Date expiration = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(name).getExpiration();

                String punishedForHowLong;
                if (expiration == null) {
                    punishedForHowLong = null;
                } else {
                    punishedForHowLong = expiration.toString();
                }

                if (reason != null) {
                    String message;

                    if (expiration == null) {
                        message = Formatters.chat("&cВы забанены навсегда!\n\n");
                    } else {
                        message = Formatters.chat("&cВы временно забанены!\n\n&7Бан действует до: &f" + punishedForHowLong + "\n");
                    }

                    message += Formatters.chat("&7Причина: &f" + reason + "\n\n&7Разбан: &b&n" + plugin.getConfig().getString("appeal_url"));

                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message);
                }
            }

        } catch (Exception e) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Пока вы подключались что-то пошло не так, попробуйте снова позднее.");
            throw e;
        }
    }

    @EventHandler
    public void onHit(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (plugin.playersInGodMode.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}
