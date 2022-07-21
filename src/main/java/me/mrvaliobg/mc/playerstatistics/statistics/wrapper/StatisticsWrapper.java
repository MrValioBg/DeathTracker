package me.mrvaliobg.mc.playerstatistics.statistics.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public abstract class StatisticsWrapper implements Listener {

    private final String statName;
    private final String dbColumnName;
    private Map<String, Integer> playersStats;
    private int counter;

    protected StatisticsWrapper(final String stat_name, final String dbColumnName) {
        this.statName = stat_name;
        this.dbColumnName = dbColumnName;
        playersStats = new HashMap<>();
    }

    protected void addCount(String uuid) {
        counter = counter + 1;
        playersStats.put(uuid, (playersStats.getOrDefault(uuid, 0)) + 1);
    }

    public void inputData(Map<String, Integer> dbData) {
        playersStats = dbData;
    }

}
