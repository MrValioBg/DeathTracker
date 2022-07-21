package me.mrvaliobg.mc.playerstatistics.configuration;

import lombok.Getter;
import me.mrvaliobg.mc.playerstatistics.logging.ClassLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;
@Getter
public enum Configuration {

    INSTANCE;

    private FileConfiguration config;

    /* Database Connection Settings */
    private String host;
    private String port;
    private String username;
    private String dbName;
    private String password;

    /* Database Additional Settings */
    private int saveDataIntervalInMinutes;
    private boolean saveDataOnStop;

    public void init(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();

        final ConfigurationSection settings = getConfig().getConfigurationSection("database");
        final ConfigurationSection credentials = getConfig().getConfigurationSection("database.credentials");

        if (credentials == null || settings == null) {
            final Logger logger = new ClassLogger(Configuration.class);
            logger.log(Level.SEVERE, "Problem getting database configuration!");
            return;
        }

        this.host = credentials.getString("host");
        this.port = credentials.getString("port");
        this.username = credentials.getString("username");
        this.dbName = credentials.getString("dbName");
        this.password = credentials.getString("password");

        this.saveDataIntervalInMinutes = settings.getInt("saveDataIntervalInMinutes");
        this.saveDataOnStop = settings.getBoolean("saveDataOnStop");
    }

}
