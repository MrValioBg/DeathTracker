package me.mrvaliobg.mc.playerstatistics.database.interfaces;

import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;

import java.util.Set;

public interface IDatabaseManager {

    void loadDataFromDB(Set<StatisticsWrapper> listStats);

    void updateStatistics(Set<StatisticsWrapper> listStats);
}
