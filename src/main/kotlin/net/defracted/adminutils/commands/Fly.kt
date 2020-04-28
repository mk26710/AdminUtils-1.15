package net.defracted.adminutils.commands

import net.defracted.adminutils.Main
import net.defracted.adminutils.util.Formatters.chat
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Fly(private val plugin: Main) : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (cmd.name.equals("fly", ignoreCase = true)) {
            if (sender is Player) {
                val p: Player = sender
                val target: Player? = if (args.isEmpty()) {
                    sender
                } else {
                    Bukkit.getPlayerExact(args[0])
                }

                if (target == null) {
                    if (!p.hasPermission("adminutils.fly.others")) {
                        p.sendMessage(chat("&cВы не можете управлять режимом полёта других игроков!"))
                        p.playSound(p.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0f)
                        return true
                    }

                    p.sendMessage(chat("&cИгрок &f${args[0]} &cне найден."))
                    return true
                }

                if (plugin.flyingPlayers.contains(target.uniqueId)) {
                    plugin.flyingPlayers.remove(target.uniqueId)
                    target.allowFlight = false
                    target.sendMessage(chat("&bРежим полёта &cвыключен&b."))
                    if (target != p) p.sendMessage(chat("&bРежим полёта &cвыключен &bдля игрока &f${target.name}&b."))
                } else {
                    plugin.flyingPlayers.add(target.uniqueId)
                    target.allowFlight = true
                    target.sendMessage(chat("&bРежим полёта &aвключен&b."))
                    if (target != p) p.sendMessage(chat("&bРежим полёта &aвключен &bдля игрока &f${target.name}&b."))
                }
            }

            return true
        }

        return false
    }
}