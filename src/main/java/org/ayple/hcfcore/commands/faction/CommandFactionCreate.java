package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandFactionCreate extends SubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "creates a faction";
    }

    @Override
    public String getSyntax() {
        return "/f create [name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 1) {
            if (args[1].length() >= 15) {
                player.sendMessage(ChatColor.RED + "Name is too long. Maximum 15 characters!");
                return;
            }

            if (args[1].length() < 3) {
                player.sendMessage(ChatColor.RED + "Name is too short. Minimum 3 characters!");
                return;
            }

            if(!validFactionName(args[1])) {
                player.sendMessage(ChatColor.RED + "Invalid name. Only letters are allowed, no spaced or numbers!");
                return;
            }

            if (NewFactionManager.playerInFaction(player.getUniqueId())) {
                player.sendMessage("Already in a faction!");
                return;
            }

            if (NewFactionManager.createNewFaction(args[1], player)) {
                player.sendMessage("Created faction!");
                return;
            }

            player.sendMessage("Faction name is already taken!");
            return;
        }

        player.sendMessage(ChatColor.RED + getSyntax());
    }

    // cba learning how to do regex on java so we are using this
    private boolean validFactionName(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
}
