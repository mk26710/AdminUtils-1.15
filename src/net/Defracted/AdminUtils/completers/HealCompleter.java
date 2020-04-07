package net.Defracted.AdminUtils.completers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class HealCompleter implements TabCompleter {
    private List<String> arguments = new ArrayList<>();


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        arguments.add("test");

        

        return arguments;
    }
}
