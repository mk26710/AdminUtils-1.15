package net.defracted.adminutils

import net.defracted.adminutils.commands.*
import net.defracted.adminutils.completers.AdminUtilsCompleter
import net.defracted.adminutils.completers.BanCompleter
import net.defracted.adminutils.completers.HealAndGodCompleter
import net.defracted.adminutils.completers.NoCompletion
import net.defracted.adminutils.listeners.ChatListener
import net.defracted.adminutils.listeners.ConnectionListener
import net.defracted.adminutils.listeners.DamageListener
import net.defracted.adminutils.mutes.MutedInfo

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

import java.util.*
import java.util.logging.Logger

import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Main : JavaPlugin() {
    val lastDeathsLocations = HashMap<UUID, Location>()
    val playersInGodMode = ArrayList<UUID>()
    val mutedPlayers = HashMap<UUID, MutedInfo>()

    companion object {
        val logger: Logger = Bukkit.getLogger()
    }

    private fun loadCommand(command: String, Executor: CommandExecutor, Completer: TabCompleter?) {
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
        loadListener(ChatListener(this))

        loadCommand("ping", Ping(), NoCompletion())
        loadCommand("heal", Heal(), HealAndGodCompleter())
        loadCommand("god", God(this), HealAndGodCompleter())
        loadCommand("ban", Ban(this), BanCompleter())
        loadCommand("adminutils", AdminUtils(this), AdminUtilsCompleter())
        loadCommand("back", Back(this), NoCompletion())
    }

    override fun onDisable() {
        playersInGodMode.clear()
    }
}