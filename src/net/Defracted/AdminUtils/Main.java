package net.Defracted.AdminUtils;

import net.Defracted.AdminUtils.commands.God;
import net.Defracted.AdminUtils.commands.Heal;
import net.Defracted.AdminUtils.completers.HealAndGodCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin {
    public List<UUID> playersInGodMode = new ArrayList<>();

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new MainListener(this), this);

        this.getCommand("heal").setExecutor(new Heal());
        this.getCommand("heal").setTabCompleter(new HealAndGodCompleter());

        this.getCommand("god").setExecutor(new God(this));
        this.getCommand("god").setTabCompleter(new HealAndGodCompleter());
    }

    @Override
    public void onDisable() {
        playersInGodMode.clear();
    }
}