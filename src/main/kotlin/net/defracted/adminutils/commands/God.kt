package net.defracted.adminutils.commands

import net.defracted.adminutils.Main
import net.defracted.adminutils.util.Formatters.chat

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


class God(private val plugin: Main) : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (cmd.name.equals("god", ignoreCase = true)) {
            if (sender !is Player) {
                return true
            }
            val p = sender
            val target: Player?
            if (args.isEmpty()) {
                target = p
            } else {
                target = Bukkit.getPlayerExact(args[0])
                if (target == null) {
                    p.sendMessage(chat("&eИгрок &c" + args[0] + " &eне найден."))
                    return true
                }
            }
            if (!plugin.playersInGodMode.contains(target.uniqueId)) {
                plugin.playersInGodMode.add(target.uniqueId)
                if (target !== p) {
                    p.sendMessage(chat("&eВы &aвключили неуязвимсть для игрока &d" + target.name + "&e."))
                }
                target.sendMessage(chat("&eВы &aвключили &eнеуязвимость."))
            } else {
                plugin.playersInGodMode.remove(target.uniqueId)
                if (target !== p) {
                    p.sendMessage(chat("&eВы &cвыключили неуязвимсть для игрока &d" + target.name + "&e."))
                }
                target.sendMessage(chat("&eВы &cвыключили &eнеуязвимость."))
            }
            return true
        }
        return false
    }
}