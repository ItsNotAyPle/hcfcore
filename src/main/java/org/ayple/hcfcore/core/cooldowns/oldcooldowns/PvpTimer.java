package org.ayple.hcfcore.core.cooldowns.oldcooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;

public class PvpTimer extends BukkitRunnable {
    private Player owner;
    public final Objective objective;
    public Objective getObjective() { return this.objective; }

    private int seconds_left;
    public int getSecondsLeft() { return this.seconds_left; }



    public PvpTimer(Player owner) {
        this.owner = owner;
        this.seconds_left = 1800;
        this.objective =  this.owner.getScoreboard().getObjective("scoreboard");
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        if (seconds_left == 0) {
            CooldownManager.onFinishedPvpTimer(owner.getUniqueId());
            this.objective.getScoreboard().resetScores(ChatColor.GREEN + "Pvp Timer: ");
            cancel();
        } else {
            this.objective.getScore(ChatColor.GREEN + "Pvp Timer: ").setScore(seconds_left);
            seconds_left--;
        }
    }
}