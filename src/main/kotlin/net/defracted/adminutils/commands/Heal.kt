package net.defracted.adminutils.commands

import net.defracted.adminutils.util.Formatters.chat

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


class Heal : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (cmd.getName().equals("heal", ignoreCase = true)) {
            if (sender !is Player) {
                return true
            }
            val p = sender
            val target: Player?

            if (args.size <= 0) {
                target = p
            } else {
                target = Bukkit.getPlayerExact(args[0])
                if (target == null) {
                    p.sendMessage(chat("&eИгрок &c" + args[0] + " &eне найден."))
                    return true
                }
            }

            target.health = 20.0
            target.foodLevel = 20

            if (target !== p) {
                p.sendMessage(chat("&eВы вылечили игрока &a" + target.name + "&e."))
            }

            target.sendMessage(chat("&eВы исцелены."))
            target.playSound(target.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)

            return true
        }
        return false
    }
}