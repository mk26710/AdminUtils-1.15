package net.defracted.adminutils.commands

import net.defracted.adminutils.Main
import net.defracted.adminutils.util.Formatters
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.*

class Mute(private val plugin: Main) : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (cmd.name.equals("mute", ignoreCase = true)) {
            if (args.isEmpty()) {
                sender.sendMessage(Formatters.chat("&cВы не указали цель."))
                return true
            }

            if (args.size < 2) {
                sender.sendMessage(Formatters.chat("&cВы не указали длительность мута."))
                return true
            }

            // Получаем цель бана
            val target = Bukkit.getPlayerExact(args[0])

            if (target == null) {
                sender.sendMessage(Formatters.chat("&cИгрок &a${args[0]} &cне найден."))
                return true
            }

            // Длительность мута
            val duration: Int = if (Formatters.isStrNum(args[1])) {
                args[1].toInt()
            } else {
                sender.sendMessage(Formatters.chat("&cУкажите срок действия бана в минутах!"))
                return true
            }

            // Получение причины из аргументов
            var reason = ""
            if (args.size <= 2) {
                reason += "не указана."
            } else {
                for (i in 2 until args.size) reason += args[i] + if (i != args.size - 1) " " else ""
            }

            // Формируем временную метку, когда мут будет снят
            val expireMuteAt = System.currentTimeMillis() + duration * 60000
            val expiresMuteAtDate = Date(expireMuteAt).toString()

            // Формируем информацию о муте и выдаём его
            plugin.muteManager.addMute(target.uniqueId, sender.name, reason, expireMuteAt)

            sender.sendMessage(Formatters.chat("&aУспешно выдан мут игроку &f${target.name} &aдо &f${expiresMuteAtDate} &aс причиной: &f$reason"))
            return true
        }

        return false
    }
}