package org.ayple.hcfcore.core.scoreboard;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.helpers.DateTimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TimerSidebarProvider implements SidebarProvider {
    @Override
    public List<SidebarEntry> getLines(Player player) {
        List<SidebarEntry> lines = new ArrayList<>();

        if (player == null) {
            return lines;
        }

        if (Hcfcore.getInstance().serverInSOTWMode()) {
            lines.add(new SidebarEntry(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD, "SOTW", ChatColor.GRAY + ": " + ChatColor.GOLD +
                    DateTimeUtils.formatSecondsToHoursMinutesSeconds(Hcfcore.getInstance().getSOTWSecondsLeft())));
        }

        if (CooldownManager.playerHasPvpTimer(player.getUniqueId())) {
            lines.add(new SidebarEntry(ChatColor.GREEN.toString() + ChatColor.BOLD, "PVP TIMER", ChatColor.GRAY + ": " + ChatColor.GOLD +
                    DateTimeUtils.formatSecondsToMinutesSeconds(CooldownManager.getSecondsLeftOfPvpTimer(player.getUniqueId()))));
        }

        if (CooldownManager.hasEnderpearlCooldown(player.getUniqueId())) {
            lines.add(new SidebarEntry(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD, "ENDERPEARL TIMER", ChatColor.GRAY + ": " + ChatColor.GOLD +
                    DateTimeUtils.formatSecondsToMinutesSeconds(CooldownManager.getSecondsLeftOfEnderpearlCooldown(player.getUniqueId()))));
        }

        if (CooldownManager.hasCrappleCooldown(player.getUniqueId())) {
            lines.add(new SidebarEntry(ChatColor.GOLD.toString() + ChatColor.BOLD, "CRAPPLE TIMER", ChatColor.GRAY + ": " + ChatColor.GOLD +
                    DateTimeUtils.formatSecondsToMinutesSeconds(CooldownManager.getSecondsLeftOfCrappleCooldown(player.getUniqueId()))));
        }

        if (CooldownManager.hasCombatTimer(player.getUniqueId())) {
            lines.add(new SidebarEntry(ChatColor.DARK_RED.toString() + ChatColor.BOLD, "COMBAT TIMER", ChatColor.GRAY + ": " + ChatColor.GOLD +
                    DateTimeUtils.formatSecondsToMinutesSeconds(CooldownManager.getSecondsLeftOfCombatTimer(player.getUniqueId()))));
        }

        if (CooldownManager.hasLogoutTimer(player.getUniqueId())) {
            lines.add(new SidebarEntry(ChatColor.RED.toString() + ChatColor.BOLD, "LOGOUT TIMER", ChatColor.GRAY + ": " + ChatColor.GOLD +
                    DateTimeUtils.formatSecondsToMinutesSeconds(CooldownManager.getSecondsLeftOfLogoutTimer(player))));
        }

        if (CooldownManager.hasHomeTimer(player.getUniqueId())) {
            lines.add(new SidebarEntry(ChatColor.YELLOW.toString() + ChatColor.BOLD, "COMBAT TIMER", ChatColor.GRAY + ": " + ChatColor.GOLD +
                    DateTimeUtils.formatSecondsToMinutesSeconds(CooldownManager.getSecondsLeftOfHomeTimer(player.getUniqueId()))));
        }


        return lines;
    }
}
