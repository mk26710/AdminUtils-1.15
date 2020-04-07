package net.Defracted.AdminUtils;

import net.Defracted.AdminUtils.commands.Heal;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("heal").setExecutor(new Heal());
    }

    @Override
    public void onDisable() {

    }
}