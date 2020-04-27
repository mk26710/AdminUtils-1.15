package net.defracted.adminutils.completers

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.util.StringUtil

import java.util.*


class AdminUtilsCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, cmd: Command, label: String, args: Array<String?>): List<String> {
        val completions: MutableList<String> = ArrayList()
        val commands: MutableList<String> = ArrayList()

        if (args.size == 1) {
            commands.add("reload")
            StringUtil.copyPartialMatches(args[0]!!, commands, completions)
            Collections.sort(completions)
        }

        return completions
    }
}