package net.Defracted.AdminUtils;

import net.Defracted.AdminUtils.commands.Heal;
import net.Defracted.AdminUtils.completers.HealCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("heal").setExecutor(new Heal());
        this.getCommand("heal").setTabCompleter(new HealCompleter());
    }

    @Override
    public void onDisable() {

    }
}