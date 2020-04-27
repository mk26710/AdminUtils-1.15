package net.defracted.adminutils.commands

import net.defracted.adminutils.Main
import net.defracted.adminutils.util.Formatters
import org.bukkit.Sound

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Back(private val plugin: Main) : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (cmd.name.equals("back", ignoreCase = true)) {
            if (sender is Player) {
                val player: Player = sender

                if (!plugin.lastDeathsLocations.containsKey(player.uniqueId)) {
                    player.sendMessage(Formatters.chat("&6Локация вашей предыдущей смерти не найдена."))
                    return true
                }

                val deathLocation = plugin.lastDeathsLocations.get(player.uniqueId)

                if (deathLocation != null) {
                    player.teleport(deathLocation)
                    player.sendMessage(Formatters.chat("&6Телепортируем вас на место вашей смерти..."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f)
                }
            }
            return true
        }

        return false
    }
}