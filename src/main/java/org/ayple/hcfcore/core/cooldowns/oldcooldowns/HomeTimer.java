package org.ayple.hcfcore.core.cooldowns.oldcooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;


// I realized it would be better to store
// player and location object rather
// than doing another SQL query
// for the excact same result.
// TODO: look back on this in the future
public class HomeTimer extends BukkitRunnable {
    private Player owner;
    private Location hq;
    private Objective objective;

    private int seconds_left;
    public int getSecondsLeft() { return this.seconds_left; }


    public HomeTimer(Player owner, Location hq) {
        this.owner = owner;
        this.seconds_left = 20;
        this.hq = hq;
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);
        this.objective =  this.owner.getScoreboard().getObjective("scoreboard");

    }

    @Override
    public void run() {
        if (seconds_left == 0) {
            CooldownManager.onFinishedHomeTimer(owner.getUniqueId());
            this.objective.getScoreboard().resetScores(ChatColor.GREEN + "Home Timer:");
            owner.teleport(hq);
            cancel();
        } else {
            objective.getScore(ChatColor.GREEN + "Home Timer:").setScore(seconds_left);
            seconds_left--;
        }
    }
}
