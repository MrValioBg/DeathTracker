package me.mrvaliobg.mc.playerstatistics.configuration;

import me.mrvaliobg.mc.playerstatistics.logging.ClassLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public enum Configuration {
    INSTANCE;

    private JavaPlugin plugin;
    private FileConfiguration config;

    private String host;
    private String port;
    private String username;
    private String dbName;
    private String password;
    private int saveDataIntervalInMinutes;
    private boolean saveDataOnStop;

    public void init(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        this.plugin = plugin;
        this.config = plugin.getConfig();

        final Logger logger = new ClassLogger(Configuration.class);
        final ConfigurationSection settingsSection = getConfig().getConfigurationSection("database");
        final ConfigurationSection credentialsSection = getConfig().getConfigurationSection("database.credentials");
        if (credentialsSection == null || settingsSection == null) {
            logger.log(Level.SEVERE, "Problem getting database configuration!");
            return;
        }

        this.host = credentialsSection.getString("host");
        this.port = credentialsSection.getString("port");
        this.username = credentialsSection.getString("username");
        this.dbName = credentialsSection.getString("dbName");
        this.password = credentialsSection.getString("password");

        this.saveDataIntervalInMinutes = settingsSection.getInt("saveDataIntervalInMinutes");
        this.saveDataOnStop = settingsSection.getBoolean("saveDataOnStop");
    }

    private FileConfiguration getConfig() {
        return this.config;
    }


    public int getSaveDataIntervalInMinutes() {
        return saveDataIntervalInMinutes;
    }

    public boolean isSaveDataOnStop() {
        return saveDataOnStop;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getDbName() {
        return dbName;
    }

    public String getPassword() {
        return password;
    }

}
