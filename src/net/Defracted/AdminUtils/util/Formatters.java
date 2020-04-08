package net.Defracted.AdminUtils.util;

import net.md_5.bungee.api.ChatColor;

public class Formatters {
    public static String chat(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static boolean isStrNum(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // next method
}
