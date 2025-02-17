package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.bukkit.entity.Player;

public class CommandFactionLeader extends SubCommand {
    @Override
    public String getName() {
        return "leader";
    }

    @Override
    public String getDescription() {
        return "make a new faction member the leader";
    }

    @Override
    public String getSyntax() {
        return "/f leader [player name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        player.sendMessage("COMING SOON");
        return;
    }
}
