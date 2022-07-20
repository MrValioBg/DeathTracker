package me.mrvaliobg.mc.playerstatistics;

import me.mrvaliobg.mc.playerstatistics.commands.PlayerStatCommand;
import me.mrvaliobg.mc.playerstatistics.configuration.Configuration;
import me.mrvaliobg.mc.playerstatistics.database.DataSource;
import me.mrvaliobg.mc.playerstatistics.statistics.managers.StatisticsManager;
import me.mrvaliobg.mc.playerstatistics.utils.ScheduleUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.TimerTask;

public class Main extends JavaPlugin {

    private final StatisticsManager statisticsManager = StatisticsManager.INSTANCE;
    private Configuration config;

    @Override
    public void onEnable() {
        config = Configuration.INSTANCE;
        config.init(this);

        DataSource.init(
                config.getHost(),
                config.getUsername(),
                config.getDbName(),
                config.getPassword(),
                config.getPort());
        statisticsManager.init();

        final CommandExecutor executor = new PlayerStatCommand();
        Objects.requireNonNull(getCommand("stats")).setExecutor(executor);

        ScheduleUtils.createScheduledTask(new TimerTask() {
            @Override
            public void run() {
                statisticsManager.updateStatistics();
            }
        }, config.getSaveDataIntervalInMinutes() * 60);

    }

    @Override
    public void onDisable() {
        if (config.isSaveDataOnStop()) {
            statisticsManager.updateStatistics();
        }
    }
}
