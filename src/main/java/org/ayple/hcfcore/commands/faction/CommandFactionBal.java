package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class CommandFactionBal extends SubCommand {
    @Override
    public String getName() {
        return "bal";
    }

    @Override
    public String getDescription() {
        return "get the faction balance";
    }

    @Override
    public String getSyntax() {
        return "/f bal";
    }
    @Override
    public void perform(Player player, String[] args) {
        Faction target_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
        if (target_faction == null ) {
            player.sendMessage(ChatColor.RED + "You are not in a faction!");
            return;
        }

        player.sendMessage(ChatColor.GREEN + "Faction Balance: $" + target_faction.getFactionBal().toString());
    }
}
