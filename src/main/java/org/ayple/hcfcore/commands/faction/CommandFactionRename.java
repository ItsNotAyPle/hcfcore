package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionRename extends SubCommand {
    @Override
    public String getName() {
        return "rename";
    }

    @Override
    public String getDescription() {
        return "rename the faction";
    }

    @Override
    public String getSyntax() {
        return "/f rename [name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!(args.length > 1)) {
            player.sendMessage(ChatColor.RED + getSyntax());
            return;
        }

        if (args[1].length() > 20) {
            player.sendMessage("name is too long");
        }

        Faction faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
        if (faction == null) {
            player.sendMessage("You're not in a faction!");
            return;
        }

        if (NewFactionManager.isPlayerLeader(faction, player.getUniqueId()) || NewFactionManager.isPlayerCoLeader(faction, player.getUniqueId())) {
            NewFactionManager.renameFaction(faction.getFactionName(), args[1]);
            player.sendMessage("Renamed faction!");
            return;
        }

        player.sendMessage("You must be leader or co-leader");
    }
}
