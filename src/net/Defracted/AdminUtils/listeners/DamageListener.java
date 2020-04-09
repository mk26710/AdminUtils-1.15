package net.Defracted.AdminUtils.listeners;

import net.Defracted.AdminUtils.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    private Main plugin;

    public DamageListener(Main instance) {
        plugin = instance;
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
