package me.mrvaliobg.mc.playerstatistics;

import me.mrvaliobg.mc.playerstatistics.commands.PlayerStatCommand;
import me.mrvaliobg.mc.playerstatistics.configuration.Configuration;
import me.mrvaliobg.mc.playerstatistics.database.DataSource;
import me.mrvaliobg.mc.playerstatistics.statistics.managers.StatisticsManager;
import me.mrvaliobg.mc.playerstatistics.utils.ScheduleUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    private final StatisticsManager statisticsManager = StatisticsManager.INSTANCE;
    private Configuration config;

    @Override
    public void onEnable() {
        config = new Configuration(this);
        new DataSource(
                config.getHost(),
                config.getUsername(),
                config.getDbName(),
                config.getPassword(),
                config.getPort());

        statisticsManager.init();

        final int interval = config.getSaveDataIntervalInMinutes() * 60;
        ScheduleUtils.createScheduledTask(new StatsSavingTask(), interval, interval);

        Objects.requireNonNull(this.getCommand("stats")).setExecutor(new PlayerStatCommand());
    }

    @Override
    public void onDisable() {
        if (config.isSaveDataOnStop()) {
            statisticsManager.updateStatistics();
        }
    }

    class StatsSavingTask implements Runnable {
        @Override
        public void run() {
            statisticsManager.updateStatistics();
        }
    }

}
