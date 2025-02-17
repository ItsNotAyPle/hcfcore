package org.ayple.hcfcore.core.cooldowns.newcooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.helpers.DateTimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.UUID;

// as of 24/07/23 i learned why storing
// a Player object is stupid

public abstract class AbstractCooldown extends BukkitRunnable {

    public final UUID ownerID;

    private int seconds_left;
    public int getSecondsLeft() { return this.seconds_left; }

    public final String title;

    public AbstractCooldown(Player player_instance, int starting_seconds, String title) {
        this.seconds_left = starting_seconds;
        this.ownerID = player_instance.getUniqueId();
        this.title = title;
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);
    }

    public AbstractCooldown(Player player_instance, int starting_seconds, int delay, int period, String title) {
        this.seconds_left = starting_seconds;
        this.ownerID = player_instance.getUniqueId();
        this.title = title;
        this.runTaskTimer(Hcfcore.getInstance(), delay, period);
    }

    @Override
    public void run() {
        if (this.seconds_left == 0) {
            this.onTimerFinished();
            this.cancel();
        } else {
            this.onTimerChanged();
            this.seconds_left--;
        }
    }


    public abstract void onTimerFinished();
    public abstract void onTimerChanged();
}
