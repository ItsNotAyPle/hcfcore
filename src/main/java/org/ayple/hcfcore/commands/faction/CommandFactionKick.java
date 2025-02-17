package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.util.UUID;

public class CommandFactionKick extends SubCommand {
    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "kick a player from the faction";
    }

    @Override
    public String getSyntax() {
        return "/f kick [player name]";
    }


    // TODO: REDO THIS
    @Override
    public void perform(Player player, String[] args) {
        if (args.length <= 1) {
            player.sendMessage(ChatColor.RED + getSyntax());
            return;
        }

        Faction player_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
        if (player_faction == null) {
            player.sendMessage(ChatColor.RED + "You are not in a faction!");
            return;
        }


        if (!(NewFactionManager.isPlayerLeader(player_faction, player.getUniqueId()) || NewFactionManager.isPlayerCoLeader(player_faction, player.getUniqueId()))) {
            player.sendMessage(ChatColor.RED + "You are not a leader or coleader!");
            return;
        }

        OfflinePlayer target_player = Bukkit.getOfflinePlayer(args[1]);
        if (player_faction.getFactionMembers().get(target_player.getUniqueId()) == 3) {
            player.sendMessage(ChatColor.RED + "That player is the leader and can't be kicked!");
            return;
        }

        NewFactionManager.kickPlayerFromFaction(player_faction, target_player);

        player.sendMessage(ChatColor.GREEN + "Kicked " + target_player.getName());

        if (target_player.isOnline()) {
            target_player.getPlayer().sendMessage("You have been kicked from " + player_faction.getFactionName());
        }
    }
}
