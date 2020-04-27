package net.defracted.adminutils.commands

import net.defracted.adminutils.util.Formatters

import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Ping : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (cmd.name.equals("ping", ignoreCase = true)) {
            if (sender is Player) {
                val player: Player = sender

                val entityPlayer: Any = player.javaClass.getMethod("getHandle").invoke(player)
                val ping = entityPlayer.javaClass.getField("ping").get(entityPlayer)

                player.sendMessage(Formatters.chat("&bВаш пинг: $ping мс &7(приблизитльно)"))
                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
            }
            return true
        }

        return false
    }
}