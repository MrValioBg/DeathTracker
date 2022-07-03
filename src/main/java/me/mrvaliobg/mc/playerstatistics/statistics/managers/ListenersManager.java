package me.mrvaliobg.mc.playerstatistics.statistics.managers;

import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public final class ListenersManager {

    private ListenersManager(){}

    public static void registerListeners(Collection<StatisticsWrapper> listeners) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        for (final Listener listener : listeners) {
            pluginManager.registerEvents(listener, JavaPlugin.getProvidingPlugin(listener.getClass()));
        }
    }

}
