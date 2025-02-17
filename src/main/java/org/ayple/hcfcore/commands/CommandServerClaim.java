package org.ayple.hcfcore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandServerClaim implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (!player.hasPermission("hcf.core.command.serverclaim")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }

        if (args.length == 0) {
            return false;
        }



        return true;
    }
}
