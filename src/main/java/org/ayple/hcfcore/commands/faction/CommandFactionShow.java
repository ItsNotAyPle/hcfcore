package org.ayple.hcfcore.commands.faction;


import org.ayple.hcfcore.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandFactionShow extends SubCommand {
    @Override
    public String getName() {
        return "Show";
    }

    @Override
    public String getDescription() {
        return "Show information about a faction";
    }

    @Override
    public String getSyntax() {
        return "/f who";
    }

    @Override
    public void perform(Player player, String[] args) {
        player.sendMessage(ChatColor.YELLOW + "Use /f who");
    }
}
