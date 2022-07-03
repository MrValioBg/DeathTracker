package me.mrvaliobg.mc.playerstatistics.statistics.wrapper;

import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public abstract class StatisticsWrapper implements Listener {

    //Statistic should be able to have command for checking top 3 players who have those statistics
    //Statistic examples: sheep killed, mobs killed, players killed, blocks mined
    //Save statistics to DB interval (get information from config) / save only on disable plugin

    private Map<String, Integer> playersStats;

    private final String statName;
    private int counter;

    protected StatisticsWrapper(final String stat_name) {
        this.statName = stat_name;
        playersStats = new HashMap<>();
    }

    public void inputData(Map<String, Integer> dbData) {
        playersStats = dbData;
    }

    protected void addCount(String uuid, int count) {
        counter = counter + count;
        playersStats.put(uuid, playersStats.get(uuid) + count);
    }

    public String getName() {
        return this.statName;
    }

    protected int getCountAfterRestart() {
        return this.counter;
    }
}
