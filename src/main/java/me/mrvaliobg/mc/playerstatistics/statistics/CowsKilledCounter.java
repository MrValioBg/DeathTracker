package me.mrvaliobg.mc.playerstatistics.statistics;

import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class CowsKilledCounter extends StatisticsWrapper {

    public CowsKilledCounter() {
        super("Cows Kill Count", "cows_killed_count");
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        final Entity entity = event.getEntity();
        if (entity instanceof Cow) {
            final Player player = ((Cow) entity).getKiller();

            if (player != null) super.addCount(player.getUniqueId().toString());
        }
    }

}
