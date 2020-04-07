package net.Defracted.AdminUtils.commands;

import net.Defracted.AdminUtils.Main;
import net.Defracted.AdminUtils.util.Formatters;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminUtils implements CommandExecutor {
    static Main plugin;

    public AdminUtils(Main instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // /adminutiles
        if (cmd.getName().equalsIgnoreCase("adminutils")) {
            // /adminutils reload
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                sender.sendMessage(Formatters.chat("&aAdminUtils reloaded."));
                return true;
            }

            return true;
        }

        return false;
    }
}
