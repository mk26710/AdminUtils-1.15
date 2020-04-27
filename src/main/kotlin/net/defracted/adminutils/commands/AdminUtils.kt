package net.defracted.adminutils.commands

import net.defracted.adminutils.Main
import net.defracted.adminutils.util.Formatters.chat

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


class AdminUtils(private val plugin: Main) : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        // /adminutiles
        if (cmd.getName().equals("adminutils", ignoreCase = true)) {
            // /adminutils reload
            if (args[0].equals("reload", ignoreCase = true)) {
                plugin.reloadConfig()
                sender.sendMessage(chat("&aAdminUtils reloaded."))
                return true
            }
            return true
        }
        return false
    }
}