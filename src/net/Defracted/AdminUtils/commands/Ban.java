package net.Defracted.AdminUtils.commands;

import net.Defracted.AdminUtils.util.Formatters;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Date;

// Нужно завершить
public class Ban implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("ban")) {
            // Получаем цель бана
            String target = args[0];

            if (args.length < 2) {
                sender.sendMessage(Formatters.chat("&cВы не указали длительность бана."));
                return true;
            }

            // Получаем срок бана (в минутах)
            int punishmentDuration;
            if (Formatters.isStrNum(args[1])) {
                punishmentDuration = Integer.parseInt(args[1]);
            } else {
                sender.sendMessage(Formatters.chat("&cУкажите срок действия бана в минутах!"));
                return true;
            }

            // Получение причины из аргументов
            String reason;
            if (args.length <= 2) {
                reason = "не указана.";
            } else {
                reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
            }

            Date punishmentDateExpiration;
            if (punishmentDuration > 0) {
                long currentMilliseconds = System.currentTimeMillis();
                punishmentDateExpiration = new Date(currentMilliseconds + (punishmentDuration * 60000));
            } else {
                punishmentDateExpiration = null;
            }

            // Баним цель на указанный срок
            Bukkit.getBanList(BanList.Type.NAME).addBan(target, reason, punishmentDateExpiration, sender.getName());

            String infoExpiration;
            if (punishmentDuration <= 0) {
                infoExpiration = "&fнавсегда";
            } else {
                infoExpiration = "&fдо " + punishmentDateExpiration.toString();
            }

            // Кикаем цель, но если игрок в онлайне
            Player playerTarget = Bukkit.getPlayerExact(target);

            if (playerTarget != null) {
                playerTarget.kickPlayer(Formatters.chat("&cВы были забанены!\n\n&fПереподключитесь, чтобы узнать подробности."));
            }

            sender.sendMessage(Formatters.chat("&aИгрок &f" + target + "&a забанен " + infoExpiration + " &aпо причине: &f" + reason));
            return true;
        }

        return false;
    }

}
