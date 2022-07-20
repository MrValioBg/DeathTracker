package me.mrvaliobg.mc.playerstatistics.statistics;

import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathCounter extends StatisticsWrapper {

    public DeathCounter() {
        super("Death Count", "death_count");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        super.addCount(event.getEntity().getUniqueId().toString());
    }

}
