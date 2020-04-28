package net.defracted.adminutils.commands

import net.defracted.adminutils.Main
import net.defracted.adminutils.util.Formatters.chat
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
                    player.sendMessage(chat("&cЛокация вашей предыдущей смерти не найдена."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0f)
                    return true
                }

                val deathLocation = plugin.lastDeathsLocations.get(player.uniqueId)

                if (deathLocation != null) {
                    player.teleport(deathLocation)
                    player.sendMessage(chat("&bТелепортируем вас на место вашей смерти..."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f)
                    plugin.lastDeathsLocations.remove(player.uniqueId)
                    player.sendMessage(chat("&c&lВНИМАНИЕ! &bВы больше не сможете вернуться сюда!"))
                }
            }
            return true
        }

        return false
    }
}