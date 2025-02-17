package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionDisband extends SubCommand {
    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public String getDescription() {
        return "disband the faction";
    }

    @Override
    public String getSyntax() {
        return "/f disband";
    }

    // TODO: test this
    @Override
    public void perform(Player player, String[] args) {
        Faction faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
        if (faction == null) {
            player.sendMessage(ChatColor.RED + "You are not in a faction!");
            return;
        }


        if (!NewFactionManager.isPlayerLeader(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You need to be leader!");
            return;
        }

        if (faction.onDtrFreeze()) {
            player.sendMessage(ChatColor.RED + "Your faction is on DTR freeze!");
        }

        NewFactionManager.disbandFaction(faction.getFactionID());
        player.sendMessage(ChatColor.GREEN + "Your faction has been disbanded!");

    }
}
