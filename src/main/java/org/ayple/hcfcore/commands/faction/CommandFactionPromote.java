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
import java.util.UUID;

public class CommandFactionPromote extends SubCommand {
    @Override
    public String getName() {
        return "promote";
    }

    @Override
    public String getDescription() {
        return "promote a faction member";
    }

    @Override
    public String getSyntax() {
        return "/f promote [player name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + getSyntax());
            return;
        }
        if (!NewFactionManager.isPlayerLeader(player.getUniqueId())) {
            player.sendMessage("You have to be leader to promote!");
            return;
        }


        UUID target_player_id = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
        Faction faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
        if (faction == null) {
            player.sendMessage(ChatColor.RED + "You are not in a faction!");
            return;
        }

        if (!faction.getFactionMembers().containsKey(target_player_id)) {
            player.sendMessage(ChatColor.RED + "That player isn't in your faction!");
            return;
        }


        if (faction.getFactionMembers().get(target_player_id) == 2) {
            player.sendMessage(ChatColor.RED + "Player cant be promoted any higher!");
            return;
        }

        NewFactionManager.promotePlayer(faction, target_player_id);
        player.sendMessage(ChatColor.GREEN + "Promoted");

    }
}
