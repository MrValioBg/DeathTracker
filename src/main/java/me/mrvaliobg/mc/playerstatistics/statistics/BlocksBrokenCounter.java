package me.mrvaliobg.mc.playerstatistics.statistics;

import me.mrvaliobg.mc.playerstatistics.statistics.wrapper.StatisticsWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlocksBrokenCounter extends StatisticsWrapper {

    public BlocksBrokenCounter() {
        super("Blocks Broken Count", "block_break_count");
    }

    @EventHandler(ignoreCancelled = true)
    public void onKill(BlockBreakEvent event) {
        final Player p = event.getPlayer();
        super.addCount(p.getUniqueId().toString());
    }

}
