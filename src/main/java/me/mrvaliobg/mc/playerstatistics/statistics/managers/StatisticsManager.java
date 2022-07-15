package me.mrvaliobg.mc.playerstatistics.statistics.managers;

import me.mrvaliobg.mc.playerstatistics.database.DatabaseManager;
import me.mrvaliobg.mc.playerstatistics.database.interfaces.IDatabaseManager;
import me.mrvaliobg.mc.playerstatistics.logging.ClassLogger;
import me.mrvaliobg.mc.playerstatistics.statistics.DeathCounter;
import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;
import me.mrvaliobg.mc.playerstatistics.utils.ListenersUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum StatisticsManager {

    INSTANCE;

    private final Set<StatisticsWrapper> listeners = new HashSet<>();
    private final IDatabaseManager databaseManager = new DatabaseManager();
    private Logger logger;

    public void init() {
        this.logger = new ClassLogger(StatisticsManager.class);
        registerWrappers();

        databaseManager.loadDataFromDB(listeners);
        logger.log(Level.INFO, "Initializing the StatisticsManager and loading database records.");
    }

    public void updateStatistics() {
        databaseManager.updateStatistics(listeners);
        logger.log(Level.INFO, "Updating database records.");
    }

    public Map<String, Integer> getPlayerStats(String uuid) {
        final Map<String, Integer> allStats = new HashMap<>();

        listeners.forEach(listener -> {
            final Map<String, Integer> stats = listener.getPlayersStats();
            if (stats.containsKey(uuid)) {
                allStats.put(listener.getName(), stats.get(uuid));
            }
        });
        return allStats;
    }

    private void registerWrappers() {
        registerStat(new DeathCounter());
        ListenersUtils.registerListeners(listeners);

        logger.log(Level.INFO, "Registering statistics types.");
    }

    private void registerStat(StatisticsWrapper statsWrapper) {
        listeners.add(statsWrapper);
    }
}
