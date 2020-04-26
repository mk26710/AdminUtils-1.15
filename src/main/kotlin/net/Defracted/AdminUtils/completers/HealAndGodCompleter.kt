package net.Defracted.AdminUtils.completers

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.util.*


class HealAndGodCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, cmd: Command, label: String, args: Array<String?>): List<String> {
        val completions: MutableList<String> = ArrayList()

        if (args.size == 1) {
            val players: MutableList<String> = ArrayList()
            Bukkit.getServer().onlinePlayers.forEach { p: Player? -> players.add(p!!.name) }
            StringUtil.copyPartialMatches(args[0]!!, players, completions)
            completions.sort()
        }

        return completions
    }
}