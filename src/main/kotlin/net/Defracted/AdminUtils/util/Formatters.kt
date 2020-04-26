package net.Defracted.AdminUtils.util

import net.md_5.bungee.api.ChatColor


object Formatters {
    fun chat(str: String?): String {
        return ChatColor.translateAlternateColorCodes('&', str)
    }

    fun isStrNum(str: String): Boolean {
        return try {
            str.toInt()
            true
        } catch (e: Exception) {
            false
        }
    }
}