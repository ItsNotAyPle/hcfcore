package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLives implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        player.sendMessage(ChatColor.GREEN + "Lives: " + PlayerDataHandler.getPlayerData(player.getUniqueId()).getLives());
        return true;
    }
}
