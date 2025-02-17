package org.ayple.hcfcore.core.cooldowns.oldcooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;

public class CrappleCooldown extends BukkitRunnable {
    private Player owner;
    private Objective objective;
    private int seconds_left;
    public int getSecondsLeft() { return this.seconds_left; }

    public CrappleCooldown(Player owner) {
        this.owner = owner;
        this.seconds_left = 60;
        this.objective =  this.owner.getScoreboard().getObjective("scoreboard");
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        if (seconds_left == 0) {
            CooldownManager.onFinishedCrappleCooldown(this.owner.getUniqueId());
            this.objective.getScoreboard().resetScores(ChatColor.GOLD + "Crapple Cooldown:");
            cancel();
        } else {
            objective.getScore(ChatColor.GOLD + "Crapple Cooldown:").setScore(seconds_left);
            seconds_left--;
        }
    }
}