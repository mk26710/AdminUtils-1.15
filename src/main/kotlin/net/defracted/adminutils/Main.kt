package net.defracted.adminutils

import net.defracted.adminutils.commands.*
import net.defracted.adminutils.completers.AdminUtilsCompleter
import net.defracted.adminutils.completers.BanAndMuteCompleter
import net.defracted.adminutils.completers.NoCompletion
import net.defracted.adminutils.completers.PlayerNameTarget
import net.defracted.adminutils.listeners.ChatListener
import net.defracted.adminutils.listeners.ChatSender
import net.defracted.adminutils.listeners.ConnectionListener
import net.defracted.adminutils.listeners.DamageListener
import net.defracted.adminutils.mutes.MuteManagement
import net.milkbowl.vault.chat.Chat
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.logging.Logger
import kotlin.collections.HashMap
import kotlin.collections.HashSet

/**
 * Project: AdminUtils
 * @author defracted
 */

class Main : JavaPlugin() {
    var chat: Chat? = null
    val muteManager = MuteManagement()

    val lastDeathsLocations = HashMap<UUID, Location>()
    val godModePlayers = HashSet<UUID>()
    val flyingPlayers = HashSet<UUID>()

    val isSenderEnabled: Boolean = this.config.getBoolean("use_custom_chat_listener")
    val isVaultEnabled: Boolean = this.config.getBoolean("use_vault")

    companion object {
        val logger: Logger = Bukkit.getLogger()
    }

    override fun onEnable() {
        saveDefaultConfig()

        if (isVaultEnabled && isSenderEnabled) setupChat()

        loadCommand("ping", Ping(), NoCompletion())

        loadCommand("back", Back(this), NoCompletion())
        loadCommand("heal", Heal(), PlayerNameTarget())
        loadCommand("god", God(this), PlayerNameTarget())
        loadCommand("fly", Fly(this), PlayerNameTarget())

        loadCommand("ban", Ban(this), BanAndMuteCompleter())
        loadCommand("mute", Mute(this), BanAndMuteCompleter())
        loadCommand("unmute", Unmute(this), PlayerNameTarget())

        loadCommand("adminutils", AdminUtils(this), AdminUtilsCompleter())

        loadListener(ConnectionListener(this))
        loadListener(DamageListener(this))
        loadListener(ChatListener(this))
        if (isSenderEnabled) loadListener(ChatSender(this))
    }

    override fun onDisable() {
        godModePlayers.clear()
        lastDeathsLocations.clear()
        flyingPlayers.clear()
    }

    private fun setupChat(): Boolean {
        val rsp = Bukkit.getServer().servicesManager.getRegistration(Chat::class.java)
        chat = rsp!!.provider
        return chat != null
    }

    private fun loadCommand(command: String, Executor: CommandExecutor, Completer: TabCompleter?) {
        this.getCommand(command)?.setExecutor(Executor)
        this.getCommand(command)?.tabCompleter = Completer
    }

    private fun loadListener(listener: Listener) {
        this.server.pluginManager.registerEvents(listener, this)
    }
}