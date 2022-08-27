package me.mrvaliobg.mc.playerstatistics.database;

import me.mrvaliobg.mc.playerstatistics.database.interfaces.IDatabaseManager;
import me.mrvaliobg.mc.playerstatistics.database.tables.Statistics;
import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;

import java.util.Set;

public class DatabaseManager implements IDatabaseManager {

    @Override
    public void loadDataFromDB(Set<StatisticsWrapper> listStats) {
        listStats.forEach(wrapper -> wrapper.inputData(Statistics.getAllDataForStat(wrapper.getDbColumnName())));
    }

    @Override
    public void updateStatistics(Set<StatisticsWrapper> listStats) {
        listStats.forEach(wrapper -> wrapper.getPlayersStats()
                .forEach((playerUUID, statValue) ->
                        Statistics.setStatisticsIntData(playerUUID, statValue, wrapper.getDbColumnName())));
    }

}
