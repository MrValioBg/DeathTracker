package me.mrvaliobg.mc.playerstatistics.statistics.wrapper;

import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public abstract class StatisticsWrapper implements Listener {

    //Statistic should be able to have command for checking top 3 players who have those statistics
    //Statistic examples: sheep killed, mobs killed, players killed, blocks mined
    //Save statistics to DB interval (get information from config) / save only on disable plugin

    private final String statName;
    private final String dbColumnName;
    private Map<String, Integer> playersStats;
    private int counter;
    protected StatisticsWrapper(final String stat_name, final String dbColumnName) {
        this.statName = stat_name;
        this.dbColumnName = dbColumnName;
        playersStats = new HashMap<>();
    }

    public Map<String, Integer> getPlayersStats() {
        return playersStats;
    }

    public void inputData(Map<String, Integer> dbData) {
        playersStats = dbData;
    }

    protected void addCount(String uuid) {
        counter = counter + 1;
        playersStats.put(uuid, (playersStats.getOrDefault(uuid, 0)) + 1);
    }

    public String getName() {
        return this.statName;
    }

    public String getDbColumnName() {
        return this.dbColumnName;
    }

    protected int getCountAfterRestart() {
        return this.counter;
    }
}
