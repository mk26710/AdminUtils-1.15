package net.Defracted.AdminUtils.completers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HealAndGodCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            List<String> players = new ArrayList<>();

            Bukkit.getServer().getOnlinePlayers().forEach(p -> players.add(p.getName()));
            StringUtil.copyPartialMatches(args[0], players, completions);
            Collections.sort(completions);
        }

        return completions;
    }
}
