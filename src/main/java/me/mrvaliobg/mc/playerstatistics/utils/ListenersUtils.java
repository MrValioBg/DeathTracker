package me.mrvaliobg.mc.playerstatistics.utils;

import me.mrvaliobg.mc.playerstatistics.Main;
import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public final class ListenersUtils {

    private ListenersUtils() {
    }

    public static void registerListeners(Collection<StatisticsWrapper> listeners) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        final JavaPlugin providingPlugin = JavaPlugin.getProvidingPlugin(Main.class);

        listeners.forEach(listener -> pluginManager.registerEvents(listener, providingPlugin));
    }

}
