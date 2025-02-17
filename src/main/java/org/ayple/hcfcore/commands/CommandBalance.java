package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.BalanceHandler;
import org.ayple.hcfcore.helpers.PlayerHelpers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandBalance implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            double bal = BalanceHandler.getPlayerBalance(player);
            player.sendMessage(ChatColor.GREEN + "Balance: " + Double.toString(bal));
        }




        return true;
    }
}
