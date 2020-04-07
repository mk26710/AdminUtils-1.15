package net.Defracted.AdminUtils.commands;

import net.Defracted.AdminUtils.util.Formatters;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

// Нужно завершить
public class Ban implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ban")) {
            if (!(sender instanceof Player)) {
                return true;
            }

            // Получение причины из аргументов
            String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            sender.sendMessage(Formatters.chat("&rПричина: &e" + reason));
            return true;
        }

        return false;
    }
}
