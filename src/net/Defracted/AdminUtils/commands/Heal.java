package net.Defracted.AdminUtils.commands;

import net.Defracted.AdminUtils.util.Formatters;
import org.bukkit.Bukkit;
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
                target = Bukkit.getPlayer(args[0]);
            }

            assert target != null;

            target.setHealth(20);

            if (target != p) {
                p.sendMessage(Formatters.chat("&eВы вылечили игрока &a" + target.getName() + "&e."));
            }

            target.sendMessage("&eВы исцелены.");
            return true;
        }

        return false;
    }
}
