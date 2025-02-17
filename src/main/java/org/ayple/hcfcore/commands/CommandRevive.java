package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRevive implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        boolean force_mode = false;

        if (args.length == 0) {
            return false;
        }

        OfflinePlayer target_player = Bukkit.getOfflinePlayer(args[0]);
        if (target_player == null) {
            player.sendMessage(ChatColor.RED + "Failed to find player!");
            return true;
        }

        if (!PlayerDataHandler.playerLoggedInBefore(target_player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "That player has never logged in before lol");
            return true;
        }

        PlayerDataHandler.decrementLivesAmountForPlayer(player);
        PlayerDataHandler.incrementLivesAmountForPlayer(target_player);

        player.sendMessage(ChatColor.GREEN + "Removed 1 life and gave it to " + target_player.getName());


        return true;
    }
}