package net.Defracted.AdminUtils;

import net.Defracted.AdminUtils.commands.AdminUtils;
import net.Defracted.AdminUtils.commands.Ban;
import net.Defracted.AdminUtils.commands.God;
import net.Defracted.AdminUtils.commands.Heal;
import net.Defracted.AdminUtils.completers.AdminUtilsCompleter;
import net.Defracted.AdminUtils.completers.BanCompleter;
import net.Defracted.AdminUtils.completers.HealAndGodCompleter;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Main extends JavaPlugin {
    public List<UUID> playersInGodMode = new ArrayList<>();

    private void loadCommand(String command, CommandExecutor Executor, TabCompleter Completer) {
        Objects.requireNonNull(getCommand(command)).setExecutor(Executor);
        Objects.requireNonNull(getCommand(command)).setTabCompleter(Completer);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new MainListener(this), this);

        loadCommand("heal", new Heal(), new HealAndGodCompleter());
        loadCommand("god", new God(this), new HealAndGodCompleter());
        loadCommand("ban", new Ban(), new BanCompleter());
        loadCommand("adminutils", new AdminUtils(this), new AdminUtilsCompleter());
    }

    @Override
    public void onDisable() {
        playersInGodMode.clear();
    }
}