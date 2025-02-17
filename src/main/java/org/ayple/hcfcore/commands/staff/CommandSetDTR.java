package org.ayple.hcfcore.commands.staff;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandSetDTR extends SubCommand {
    @Override
    public String getName() {
        return "setdtr";
    }

    @Override
    public String getDescription() {
        return "Set a factions DTR";
    }

    @Override
    public String getSyntax() {
        return "/staff setdtr [faction name] [dtr]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("hcf.core.staff.command.setdtr")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return;
        }

        if (args.length < 3) {
            player.sendMessage(ChatColor.RED + getSyntax());
            return;
        }


        Faction target_faction = NewFactionManager.getFaction(args[1]);
        if (target_faction == null) {
            player.sendMessage(ChatColor.RED + "Faction not fount!");
            return;
        }

        target_faction.setFactionDTR(Float.parseFloat(args[2]));
        player.sendMessage(ChatColor.GREEN + "Set " + target_faction.getFactionName() + "'s DTR to " + args[2] + " !");
    }
}
