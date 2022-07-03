package me.mrvaliobg.mc.playerstatistics.utils;

import org.bukkit.ChatColor;

public final class ChatUtils {

    private ChatUtils() {
    }

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
