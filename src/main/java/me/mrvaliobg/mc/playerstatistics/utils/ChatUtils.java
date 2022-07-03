package me.mrvaliobg.mc.playerstatistics.utils;

import org.bukkit.ChatColor;

public class ChatUtils  {

    ChatUtils() {
    }

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
