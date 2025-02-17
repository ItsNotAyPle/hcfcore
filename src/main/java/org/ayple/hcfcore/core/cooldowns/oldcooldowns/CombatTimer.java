package org.ayple.hcfcore.core.cooldowns.oldcooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;

public class CombatTimer extends BukkitRunnable {
    private Player owner;
    private Objective objective;

    private int seconds_left;
    public int getSecondsLeft() { return this.seconds_left; }

    public CombatTimer(Player owner) {
        this.owner = owner;
        this.seconds_left = 30;
        this.objective =  this.owner.getScoreboard().getObjective("scoreboard");
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        if (seconds_left == 0) {
            CooldownManager.onCombatTimerOver(owner.getUniqueId());
            this.objective.getScoreboard().resetScores(ChatColor.DARK_RED + "Combat Cooldown: ");
            cancel();
        } else {
            objective.getScore(ChatColor.DARK_RED + "Combat Cooldown: ").setScore(seconds_left);
            seconds_left--;
        }
    }
}