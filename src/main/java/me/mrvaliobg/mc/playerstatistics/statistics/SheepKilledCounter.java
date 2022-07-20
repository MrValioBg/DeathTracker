package me.mrvaliobg.mc.playerstatistics.statistics;

import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class SheepKilledCounter extends StatisticsWrapper {

    public SheepKilledCounter() {
        super("SheepKill Count", "sheep_killed_count");
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        final Entity entity = event.getEntity();
        if (entity instanceof Sheep) {
            final Player player = ((Sheep) entity).getKiller();

            if (player != null) super.addCount(player.getUniqueId().toString());
        }
    }

}
