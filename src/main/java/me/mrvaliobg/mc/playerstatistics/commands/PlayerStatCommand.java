package me.mrvaliobg.mc.playerstatistics.commands;

import me.mrvaliobg.mc.playerstatistics.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerStatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            final Player player = (Player) commandSender;
            final String uuid = player.getUniqueId() + "";
        } else {
            commandSender.sendMessage(ChatUtils.colorize("&cYou are not a player, thus you do not have those stats!"));
        }
        return true;
    }
}
