package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.bukkit.entity.Player;

public class CommandFactionFocus extends SubCommand {
    @Override
    public String getName() {
        return "focus";
    }

    @Override
    public String getDescription() {
        return "focus a faction";
    }

    @Override
    public String getSyntax() {
        return "/f focus [faction name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        return;
    }
}
