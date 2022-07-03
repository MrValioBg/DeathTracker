package me.mrvaliobg.mc.playerstatistics.statistics.managers;

import me.mrvaliobg.mc.playerstatistics.database.DatabaseManager;
import me.mrvaliobg.mc.playerstatistics.database.interfaces.IDatabaseManager;
import me.mrvaliobg.mc.playerstatistics.statistics.DeathCounter;
import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;

import java.util.HashSet;
import java.util.Set;

public class StatisticsManager {

    private final Set<StatisticsWrapper> listenersToRegister = new HashSet<>();
    private final IDatabaseManager databaseManager = new DatabaseManager();

    public StatisticsManager() {
        loadStatistics();
        databaseManager.loadDataFromDB(listenersToRegister);
    }

    public void updateStatistics() {
        databaseManager.updateStatistics(listenersToRegister);
    }

    private void loadStatistics() {
        registerStat(new DeathCounter());
        registerListeners();
    }

    public void registerStat(StatisticsWrapper statsWrapper) {
        listenersToRegister.add(statsWrapper);
    }

    public void registerListeners() {
        ListenersManager.registerListeners(listenersToRegister);
        listenersToRegister.clear();
    }

}
