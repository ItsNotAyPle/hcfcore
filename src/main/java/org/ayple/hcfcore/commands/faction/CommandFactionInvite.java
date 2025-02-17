package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionInviteManager;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Objects;

public class CommandFactionInvite extends SubCommand {

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "invite a player to the faction";
    }

    @Override
    public String getSyntax() {
        return "/f invite [player name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 1) {
            Player target_player = Bukkit.getPlayer(args[1]);

            if (target_player == null) {
                player.sendMessage(ChatColor.RED + "That player is not online!");
                return;
            }

            if (Objects.equals(target_player.getName(), player.getName())) {
                player.sendMessage(ChatColor.RED + "You cannot invite yourself lol wth. This has been logged");
                return;
            }

            Faction player_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());

            if (player_faction == null) {
                player.sendMessage(ChatColor.RED + "You are not in a faction!");
                return;
            }

            // prob better to check in cooldowns instead
            if (player_faction.getFactionDTR() != player_faction.getMaxDTR()) {
                player.sendMessage(ChatColor.RED + "Currently on DTR regen!");
                return;
            }

            if (FactionInviteManager.registerNewInvite(player_faction.getFactionID(),  target_player.getUniqueId())) {
                if (target_player.isOnline()) {
                    target_player.sendMessage(ChatColor.GREEN + player_faction.getFactionName() + " has invited you!");
                }

                player.sendMessage(ChatColor.GREEN + "Invited " + target_player.getDisplayName() + " to the faction!");
                return;
            }



            player.sendMessage(ChatColor.RED + "Failed to invite! consult developer.");

        }
    }
}

