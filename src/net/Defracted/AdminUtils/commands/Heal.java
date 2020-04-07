package net.Defracted.AdminUtils.commands;

import net.Defracted.AdminUtils.util.Formatters;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("heal")) {
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

            target.setHealth(20);
            target.setFoodLevel(20);

            if (target != p) {
                p.sendMessage(Formatters.chat("&eВы вылечили игрока &a" + target.getName() + "&e."));
            }

            target.sendMessage(Formatters.chat("&eВы исцелены."));
            target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            return true;
        }

        return false;
    }
}
