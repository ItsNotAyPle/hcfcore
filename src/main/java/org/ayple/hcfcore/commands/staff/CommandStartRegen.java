package org.ayple.hcfcore.commands.staff;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandStartRegen extends SubCommand  {
    @Override
    public String getName() {
        return "startregen";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getSyntax() {
        return "/staff startregen";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("hcf.permission.staff.startregen")) {
            player.sendMessage(ChatColor.RED + "Invalid permission!");
            return;
        }

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + getSyntax());
            return;
        }

        Faction target_faction = NewFactionManager.getFaction(args[1]);
        if (target_faction == null) {
            player.sendMessage(ChatColor.YELLOW + "Faction not fount!");
            return;
        }

        CooldownManager.onFinishedDtrRegen(target_faction);
    }
}
