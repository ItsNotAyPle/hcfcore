package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandLogout implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (!CooldownManager.hasLogoutTimer(player.getUniqueId())) {
            CooldownManager.registerLogoutTimer(player);
        }

        return true;
    }
}