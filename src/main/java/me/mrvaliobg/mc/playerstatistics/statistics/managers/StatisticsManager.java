package me.mrvaliobg.mc.playerstatistics.statistics.managers;

import me.mrvaliobg.mc.playerstatistics.database.DatabaseManager;
import me.mrvaliobg.mc.playerstatistics.database.interfaces.IDatabaseManager;
import me.mrvaliobg.mc.playerstatistics.logging.ClassLogger;
import me.mrvaliobg.mc.playerstatistics.statistics.DeathCounter;
import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;
import me.mrvaliobg.mc.playerstatistics.utils.ListenersUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum StatisticsManager {

    INSTANCE;
    private final Set<StatisticsWrapper> listenersToRegister = new HashSet<>();
    private final IDatabaseManager databaseManager = new DatabaseManager();
    private Logger logger;

    public void init() {
        this.logger = new ClassLogger(StatisticsManager.class);
        registerWrappers();

        databaseManager.loadDataFromDB(listenersToRegister);
        logger.log(Level.INFO, "Initializing the StatisticsManager and loading database records.");
    }

    public void updateStatistics() {
        databaseManager.updateStatistics(listenersToRegister);
        logger.log(Level.INFO, "Updating database records.");
    }

    private void registerWrappers() {
        registerStat(new DeathCounter());
        registerListeners();
        logger.log(Level.INFO, "Registering statistics types.");
    }

    private void registerStat(StatisticsWrapper statsWrapper) {
        listenersToRegister.add(statsWrapper);
    }

    public void registerListeners() {
        ListenersUtils.registerListeners(listenersToRegister);
    }

}
