package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionInvite;
import org.ayple.hcfcore.core.faction.FactionInviteManager;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionJoin extends SubCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "join a faction";
    }

    @Override
    public String getSyntax() {
        return "/f join [faction name]";
    }


    // TODO: check for combat log too
    @Override
    public void perform(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + getSyntax());
            return;
        }

        if (NewFactionManager.getFactionFromPlayerID(player.getUniqueId()) != null) {
            player.sendMessage(ChatColor.RED + "You are already in a faction! do /f leave first");
            return;
        }

        Faction target_faction = NewFactionManager.getFaction(args[1]);
        if (target_faction == null) {
            player.sendMessage(ChatColor.RED + "That faction doesn't exist");
            return;
        }

        if (target_faction.factionFull()) {
            player.sendMessage(ChatColor.RED + "That faction is full!");
            return;
        }

        // prob better to check in cooldowns instead
        if (target_faction.onDtrFreeze()) {
            player.sendMessage(ChatColor.RED + "They are currently on DTR regen and you cannot join!");
            return;
        }

        if (!target_faction.getFactionInvites().contains(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You do not have an invite!");
            return;
        }


        FactionInviteManager.onPlayerJoinFaction(target_faction.getFactionID(), player);
        player.sendMessage(ChatColor.GREEN + "Joined " + ChatColor.LIGHT_PURPLE + target_faction.getFactionName() + ChatColor.GREEN + "!");

    }
}
