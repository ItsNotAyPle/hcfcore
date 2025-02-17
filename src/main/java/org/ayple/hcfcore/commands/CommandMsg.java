package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMsg implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length < 2) {
//            player.sendMessage(ChatColor.RED + "Invalid syntax!");
            return false;
        }

        Player recipient = Bukkit.getServer().getPlayer(args[0]);
        if (recipient == null || !recipient.isOnline()) {
            player.sendMessage(ChatColor.RED + "Player is not online");
            return true;
        }

        recipient.sendMessage(ChatColor.GRAY + "(From " + ChatColor.DARK_GRAY + player.getDisplayName() + ChatColor.GRAY + ")" + ChatColor.GRAY + " " + args[1]);
        player.sendMessage(ChatColor.GRAY + "(To " + ChatColor.DARK_GRAY + player.getDisplayName() + ChatColor.GRAY + ")" + ChatColor.GRAY + " " + args[1]);
        return true;
    }
}