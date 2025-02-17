package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.core.items.ClaimWand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionClaim extends SubCommand {

    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getDescription() {
        return "gives a claiming wand for claiming land";
    }

    @Override
    public String getSyntax() { return "/f claim"; }

    @Override
    public void perform(Player player, String[] args) {
        if (CooldownManager.playerHasPvpTimer(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You need to disable pvp timer! /pvpenable");
            return;
        }

        if (NewFactionManager.playerInFaction(player.getUniqueId())) {
            // wont be null as already checking if player in faction
            if (NewFactionManager.getFactionFromPlayerID(player.getUniqueId()).getClaim() != null) {
                player.sendMessage(ChatColor.RED + "You already have a claim!");
                return;
            }

            if (NewFactionManager.isPlayerLeader(player.getUniqueId())) {
                player.getInventory().addItem(ClaimWand.makeNewWand());
                player.sendMessage("You have recieved a claim wand!");

                return;
            }

            player.sendMessage("You must be leader to be able to claim!");
            return;
        }

        player.sendMessage("You are not in a faction. Create one with /f create [name]");
    }
}



//        if (player.getInventory().getItemInMainHand() == null) {
//            player.getInventory().addItem(ClaimWand.makeNewWand());
//            return;
//        }


