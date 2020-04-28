package net.defracted.adminutils.commands

import net.defracted.adminutils.Main
import net.defracted.adminutils.util.Formatters.chat

import org.bukkit.Bukkit
import org.bukkit.Sound
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
            val p: Player = sender
            val target: Player?
            if (args.isEmpty()) {
                target = p
            } else {
                if (!p.hasPermission("adminutils.god.others")) {
                    p.sendMessage(chat("&cВы не можете управлять неуязвимостью других игроков!"))
                    p.playSound(p.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0f)
                    return true
                }

                target = Bukkit.getPlayerExact(args[0])
                if (target == null) {
                    p.sendMessage(chat("&cИгрок &f" + args[0] + " &cне найден."))
                    return true
                }
            }

            if (!plugin.godModePlayers.contains(target.uniqueId)) {
                plugin.godModePlayers.add(target.uniqueId)
                if (target !== p) {
                    p.sendMessage(chat("&cВы &aвключили неуязвимсть для игрока &f" + target.name + "&c."))
                }
                target.sendMessage(chat("&bВы &aвключили &bнеуязвимость."))
            } else {
                plugin.godModePlayers.remove(target.uniqueId)
                if (target !== p) {
                    p.sendMessage(chat("&cВы &4выключили неуязвимсть для игрока &f" + target.name + "&c."))
                }
                target.sendMessage(chat("&bВы &cвыключили &bнеуязвимость."))
            }
            return true
        }

        return false
    }
}