package net.defracted.adminutils.commands

import net.defracted.adminutils.Main
import net.defracted.adminutils.util.Formatters
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class Unmute(private val plugin: Main) : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (cmd.name.equals("unmute", ignoreCase = true)) {
            if (args.isEmpty()) {
                sender.sendMessage(Formatters.chat("&cВы не указали игрока!"))
                return true
            }

            // Получаем игрока
            val target = Bukkit.getPlayerExact(args[0])

            // Проверяем, существует ли игрок
            if (target == null) {
                sender.sendMessage(Formatters.chat("&cИгрок &f${args[0]} &cне в сети."))
                return true
            }

            // Проверяем наличие мута
            if (!plugin.muteManager.mutedPlayers.contains(target.uniqueId)) {
                sender.sendMessage(Formatters.chat("&cУ игрока &f${target.name} &cне найдено активных мутов."))
                return true
            }

            // Снимаем мут
            plugin.muteManager.delMute(target.uniqueId)
            sender.sendMessage(Formatters.chat("&bВы успешно сняли мут с игрока &f${target.name}&b."))
            return true
        }

        return false
    }
}