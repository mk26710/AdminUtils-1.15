package net.Defracted.AdminUtils.commands;

import net.Defracted.AdminUtils.Main;
import net.Defracted.AdminUtils.util.Formatters;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

// Нужно завершить
public class Ban implements CommandExecutor {
    private Main plugin;

    public Ban(Main instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ban")) {
            if (args.length <= 0) {
                sender.sendMessage(Formatters.chat("&cВы не указали ник!"));
                return true;
            }

            // Получаем цель бана
            String target = args[0];

            // Проверяем наличие длительности
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

            // Создаём дату, основываясь на указанном времени
            Date punishmentDateExpiration;
            if (punishmentDuration > 0) {
                punishmentDateExpiration = new Date(System.currentTimeMillis() + (punishmentDuration * 60000));
            } else {
                punishmentDateExpiration = null;
            }

            // Получение причины из аргументов
            String reason = "";
            if (args.length <= 2) {
                reason += "не указана.";
            } else {
                for (int i = 2; i < args.length; i++)
                    reason += args[i] + (i != (args.length - 1) ? " " : "");
            }


            String infoExpiration;
            if (punishmentDuration <= 0) {
                infoExpiration = "&fнавсегда";
            } else {
                infoExpiration = "&fдо " + punishmentDateExpiration.toString();
            }

            // Кикаем цель, но если игрок в онлайне
            Player playerTarget = Bukkit.getPlayerExact(target);

            // Баним цель на указанный срок
            Bukkit.getBanList(BanList.Type.NAME).addBan(target, reason, punishmentDateExpiration, sender.getName());

            // Кикаем игрока, если он в онлайне
            if (playerTarget != null)
                playerTarget.kickPlayer(Formatters.chat("&cВы были забанены!\n\n&fПереподключитесь, чтобы узнать подробности."));

            // Информируем модератора
            sender.sendMessage(Formatters.chat("&aИгрок &f" + target + "&a забанен " + infoExpiration + " &aпо причине: &f" + reason));

            // Информируем всех игроков
            if (plugin.getConfig().getBoolean("broadcast_ban_message"))
                Bukkit.broadcastMessage(Formatters.chat("&c&lОдин из игроков был удалён из игры за использование читов или другие нарушения. " +
                        "\n&bБлагодарим вас за жалобы!"));

            return true;
        }

        return false;
    }

}
