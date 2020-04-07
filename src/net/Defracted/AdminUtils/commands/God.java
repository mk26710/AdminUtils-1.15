package net.Defracted.AdminUtils.commands;

import net.Defracted.AdminUtils.Main;
import net.Defracted.AdminUtils.util.Formatters;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class God implements CommandExecutor {
    static Main plugin;

    public God(Main instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("god")) {
            if (!(sender instanceof Player)) {
                return true;
            }

            Player p = (Player) sender;
            Player target;

            if (args.length <= 0) {
                target = p;
            } else {
                target = Bukkit.getPlayerExact(args[0]);

                if (target == null) {
                    p.sendMessage(Formatters.chat("&eИгрок &c" + args[0] + " &eне найден."));
                    return true;
                }
            }

            if (!(plugin.playersInGodMode.contains(target.getUniqueId()))) {
                plugin.playersInGodMode.add(target.getUniqueId());

                if (target != p) {
                    p.sendMessage(Formatters.chat("&eВы &aвключили неуязвимсть для игрока &d" + target.getName() + "&e."));
                }

                target.sendMessage(Formatters.chat("&eВы &aвключили &eнеуязвимость."));

            } else {
                plugin.playersInGodMode.remove(target.getUniqueId());

                if (target != p) {
                    p.sendMessage(Formatters.chat("&eВы &cвыключили неуязвимсть для игрока &d" + target.getName() + "&e."));
                }

                target.sendMessage(Formatters.chat("&eВы &cвыключили &eнеуязвимость."));
            }

            return true;
        }

        return false;
    }
}
