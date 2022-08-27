package me.mrvaliobg.mc.playerstatistics.statistics.managers;

import lombok.extern.slf4j.Slf4j;
import me.mrvaliobg.mc.playerstatistics.database.DatabaseManager;
import me.mrvaliobg.mc.playerstatistics.database.interfaces.IDatabaseManager;
import me.mrvaliobg.mc.playerstatistics.statistics.BlocksBrokenCounter;
import me.mrvaliobg.mc.playerstatistics.statistics.CowsKilledCounter;
import me.mrvaliobg.mc.playerstatistics.statistics.DeathCounter;
import me.mrvaliobg.mc.playerstatistics.statistics.SheepKilledCounter;
import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;
import me.mrvaliobg.mc.playerstatistics.utils.ListenersUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public enum StatisticsManager {

    INSTANCE;
    private final Set<StatisticsWrapper> listeners = new HashSet<>();
    private final IDatabaseManager databaseManager = new DatabaseManager();

    public void init() {
        registerStatistics();

        databaseManager.loadDataFromDB(listeners);
        log.info("Initializing the StatisticsManager and loading database records.");
    }

    public void updateStatistics() {
        databaseManager.updateStatistics(listeners);
        log.info("Updating database records.");
    }

    public Map<String, Integer> getPlayerStats(String uuid) {

        final Map<String, Integer> allStats = new HashMap<>();
        listeners.forEach(listener -> {
            final Map<String, Integer> stats = listener.getPlayersStats();
            if (stats.containsKey(uuid)) {
                allStats.put(listener.getStatName(), stats.get(uuid));
            }
        });
        return allStats;
    }

    private void registerStatistics() {
        registerStat(new DeathCounter());
        registerStat(new SheepKilledCounter());
        registerStat(new CowsKilledCounter());
        registerStat(new BlocksBrokenCounter());

        ListenersUtils.registerListeners(listeners);
        log.info("Registering statistics types.");
    }

    private void registerStat(StatisticsWrapper statsWrapper) {
        listeners.add(statsWrapper);
    }
}
