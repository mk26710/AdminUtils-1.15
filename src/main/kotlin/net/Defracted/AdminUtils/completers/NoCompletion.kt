package net.Defracted.AdminUtils.completers

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.ArrayList

class NoCompletion : TabCompleter {
    override fun onTabComplete(sender: CommandSender, cmd: Command, label: String, args: Array<String?>): List<String> {
        return ArrayList()
    }
}