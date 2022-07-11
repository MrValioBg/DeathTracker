package me.mrvaliobg.mc.playerstatistics;

import me.mrvaliobg.mc.playerstatistics.configuration.Configuration;
import me.mrvaliobg.mc.playerstatistics.database.DataSource;
import me.mrvaliobg.mc.playerstatistics.statistics.managers.StatisticsManager;
import me.mrvaliobg.mc.playerstatistics.utils.ScheduleUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    private StatisticsManager statisticsManager;
    private Configuration instance;

    @Override
    public void onEnable() {
        instance = Configuration.INSTANCE;
        instance.init(this);
        DataSource.INSTANCE.init(instance.getHost(), instance.getUsername(), instance.getDbName(), instance.getPassword(), instance.getPort());

        statisticsManager = new StatisticsManager();
        final int intervalInSeconds = instance.getSaveDataIntervalInMinutes() * 60;
        ScheduleUtils.createScheduledTask(new StatsSavingTask(), intervalInSeconds, intervalInSeconds);
    }

    @Override
    public void onDisable() {
        if (instance.isSaveDataOnStop()) {
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
