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
        if (cmd.name.equals("heal", ignoreCase = true)) {
            if (sender !is Player) {
                return true
            }
            val p = sender
            val target: Player?

            if (args.isEmpty()) {
                target = p
            } else {
                if (!p.hasPermission("adminutils.heal.others")) {
                    p.sendMessage(chat("&cВы не можете исцелять других игроков!"))
                    p.playSound(p.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0f)
                    return true
                }

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