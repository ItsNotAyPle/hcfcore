package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.entity.Player;

public class CommandFactionSetHome extends SubCommand {
    @Override
    public String getName() {
        return "sethome";
    }

    @Override
    public String getDescription() {
        return "sets the faction home";
    }

    @Override
    public String getSyntax() {
        return "/f sethome";
    }

    @Override
    public void perform(Player player, String[] args) {
        Faction faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
        if (faction == null) {
            player.sendMessage("You are not in a faction!");
            return;
        }

        if (!ClaimsManager.playerOwnsClaimTheyreIn(player)) {
            player.sendMessage("You are not in your claim!");
            return;
        }

        int player_rank = faction.getFactionMembers().get(player.getUniqueId());

        // for the life of me, i tried to get enums to work
        // someone with more java experience can do it icba
        // 30 mins of my life gone. idek why it wasnt working
        if (!(player_rank == 3 || player_rank == 2)) {
            player.sendMessage("You are not the leader or co-leader!");
            return;
        }

        NewFactionManager.setFactionHQ(faction.getFactionID(), player.getLocation());
        player.sendMessage("Set faction HQ!");
    }
}
