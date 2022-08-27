package me.mrvaliobg.mc.playerstatistics;

import me.mrvaliobg.mc.playerstatistics.commands.PlayerStatCommand;
import me.mrvaliobg.mc.playerstatistics.configuration.Configuration;
import me.mrvaliobg.mc.playerstatistics.database.DataSource;
import me.mrvaliobg.mc.playerstatistics.statistics.managers.StatisticsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    private final StatisticsManager statisticsManager = StatisticsManager.INSTANCE;
    private Configuration config;

    @Override
    public void onEnable() {
        config = Configuration.INSTANCE;
        config.init(this);

        //Create Connection with the DataBase
        DataSource.init(
                config.getHost(),
                config.getUsername(),
                config.getDbName(),
                config.getPassword(),
                config.getPort());
        statisticsManager.init();

        //Register Commands
        final CommandExecutor executor = new PlayerStatCommand();
        Objects.requireNonNull(getCommand("stats")).setExecutor(executor);

        //Setup Task for updating the database
        final int timeInTicks = config.getSaveDataIntervalInMinutes() * 60 * 20;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, statisticsManager::updateStatistics, timeInTicks, timeInTicks);
    }

    @Override
    public void onDisable() {
        if (config.isSaveDataOnStop()) {
            statisticsManager.updateStatistics();
        }
    }


}
