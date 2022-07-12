package me.mrvaliobg.mc.playerstatistics.database;

import me.mrvaliobg.mc.playerstatistics.database.interfaces.IDatabaseManager;
import me.mrvaliobg.mc.playerstatistics.database.tables.Statistics;
import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;

import java.util.Map;
import java.util.Set;

public class DatabaseManager implements IDatabaseManager {

    @Override
    public void loadDataFromDB(Set<StatisticsWrapper> listStats) {
        for (StatisticsWrapper wrapper : listStats) {
            wrapper.inputData(Statistics.getAllDataForStat(wrapper.getDbColumnName()));
        }
    }

    @Override
    public void updateStatistics(Set<StatisticsWrapper> listStats) {
        for (StatisticsWrapper wrapper : listStats) {
            for (Map.Entry<String, Integer> entry : wrapper.getPlayersStats().entrySet()) {
                Statistics.setStatisticsIntData(entry.getKey(), entry.getValue(), wrapper.getDbColumnName());
            }
        }
    }

}
