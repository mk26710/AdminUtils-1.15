package net.Defracted.AdminUtils

import net.Defracted.AdminUtils.commands.AdminUtils
import net.Defracted.AdminUtils.commands.Ban
import net.Defracted.AdminUtils.commands.God
import net.Defracted.AdminUtils.commands.Heal
import net.Defracted.AdminUtils.completers.AdminUtilsCompleter
import net.Defracted.AdminUtils.completers.BanCompleter
import net.Defracted.AdminUtils.completers.HealAndGodCompleter
import net.Defracted.AdminUtils.listeners.ConnectionListener
import net.Defracted.AdminUtils.listeners.DamageListener
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.logging.Logger
import kotlin.collections.ArrayList


class Main() : JavaPlugin() {
    val playersInGodMode = ArrayList<UUID>()

    companion object {
        val logger: Logger = Bukkit.getLogger()
    }

    private fun loadCommand(command: String, Executor: CommandExecutor, Completer: TabCompleter) {
        this.getCommand(command)?.setExecutor(Executor)
        this.getCommand(command)?.tabCompleter = Completer
    }

    private fun loadListener(listener: Listener) {
        this.server.pluginManager.registerEvents(listener, this)
    }

    override fun onEnable() {
        saveDefaultConfig()
        loadListener(ConnectionListener(this))
        loadListener(DamageListener(this))
        loadCommand("heal", Heal(), HealAndGodCompleter())
        loadCommand("god", God(this), HealAndGodCompleter())
        loadCommand("ban", Ban(this), BanCompleter())
        loadCommand("adminutils", AdminUtils(this), AdminUtilsCompleter())
    }

    override fun onDisable() {
        playersInGodMode.clear()
    }
}