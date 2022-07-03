package me.mrvaliobg.mc.playerstatistics.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public enum Configuration {
    INSTANCE;

    private JavaPlugin plugin;
    private FileConfiguration config;

    public void init(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public void getConfiguration() {


    }

}
