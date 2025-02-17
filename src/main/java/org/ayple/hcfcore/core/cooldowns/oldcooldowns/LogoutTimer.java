package org.ayple.hcfcore.core.cooldowns.oldcooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;

import java.util.UUID;

public class LogoutTimer extends BukkitRunnable {
    int seconds_left;
    Player owner;
    private Objective objective;


    public int getSecondsLeft() { return this.seconds_left; }


    public LogoutTimer(Player owner) {
        this.owner = owner;
        this.seconds_left = 30;
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);
        this.objective = this.owner.getScoreboard().getObjective("scoreboard");
    }

    @Override
    public void run() {
        if (this.seconds_left == 0) {
            owner.kickPlayer("Logged out safely.");
            this.objective.getScoreboard().resetScores(ChatColor.DARK_PURPLE + "EnderPearl Cooldown:");
            cancel();
        } else {
            objective.getScore(ChatColor.RED + "Logout Cooldown:").setScore(seconds_left);
            this.seconds_left--;
        }
    }
}
