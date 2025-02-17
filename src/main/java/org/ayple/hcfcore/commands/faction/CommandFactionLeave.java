package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommandFactionLeave extends SubCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "leave the faction youre in";
    }

    @Override
    public String getSyntax() {
        return "/f leave";
    }

    @Override
    public void perform(Player player, String[] args) {
        Faction target_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());

        if (target_faction == null) {
            player.sendMessage(ChatColor.RED + "You are not in a faction!");
            return;
        }

        if (NewFactionManager.isPlayerLeader(target_faction, player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You cannot leave since you are the leader. Assign a new leader or /f disband");
            return;
        }

        NewFactionManager.kickPlayerFromFaction(target_faction, player);
        player.sendMessage(ChatColor.GREEN + "Left " + ChatColor.LIGHT_PURPLE + target_faction.getFactionName() + ChatColor.GREEN + "!");
    }
}
