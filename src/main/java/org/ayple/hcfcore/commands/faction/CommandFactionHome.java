package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CommandFactionHome extends SubCommand {
    @Override
    public String getName() {
        return "home";
    }

    @Override
    public String getDescription() {
        return "teleport to your factions home";
    }

    @Override
    public String getSyntax() {
        return "/f home";
    }

    @Override
    public void perform(Player player, String[] args) {
        Faction player_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
        if (player_faction == null) {
            player.sendMessage(ChatColor.RED + "You are not in a faction.");
            return;
        }

        Location hq = player_faction.getFactionHQ();
        if (hq == null) {
            player.sendMessage(ChatColor.RED + "Faction does not have a home set");
            return;
        }

        if (CooldownManager.playerHasPvpTimer(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You need to disable your pvp timer. Do /pvpenable");
            return;
        }


        if (!CooldownManager.hasHomeTimer(player.getUniqueId())) {
            CooldownManager.registerHomeTimer(player, hq);
        }
    }
}
