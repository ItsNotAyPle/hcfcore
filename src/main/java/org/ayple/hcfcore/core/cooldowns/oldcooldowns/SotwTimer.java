package org.ayple.hcfcore.core.cooldowns.oldcooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;



// TODO: implement this with the new scoreboard in the future
// TODO: for now just state the times in chat every 5 mins.
public class SotwTimer extends BukkitRunnable {
    private Player owner;


    private int seconds_left;

    public int getSecondsLeft() {
        return this.seconds_left;
    }


    public SotwTimer() {
        this.owner = owner;
        this.seconds_left = 3600; // 1 hour sotw
        Hcfcore.getInstance().enableSOTWMode();
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        if (seconds_left == 0) {
            Hcfcore.getInstance().disableSOTWMode();
            cancel();

        } else {
            seconds_left--;
        }
    }
}
