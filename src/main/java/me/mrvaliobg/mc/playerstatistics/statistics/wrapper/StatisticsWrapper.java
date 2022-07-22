package me.mrvaliobg.mc.playerstatistics.statistics.wrapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter @RequiredArgsConstructor
public abstract class StatisticsWrapper implements Listener {

    private final String statName;
    private final String dbColumnName;
    private Map<String, Integer> playersStats = new HashMap<>();
    private int counter;

    protected void addCount(String uuid) {
        counter = counter + 1;
        playersStats.put(uuid, (playersStats.getOrDefault(uuid, 0)) + 1);
    }

    public void inputData(Map<String, Integer> dbData) {
        playersStats = dbData;
    }

}
