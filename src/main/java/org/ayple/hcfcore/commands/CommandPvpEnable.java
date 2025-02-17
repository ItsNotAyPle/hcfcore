package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPvpEnable implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (CooldownManager.playerHasPvpTimer(player.getUniqueId())) {
            CooldownManager.cancelPvpTimer(player.getUniqueId());
            player.sendMessage(ChatColor.BLUE + "Cancelled pvp timer!");
        }

        return true;
    }
}