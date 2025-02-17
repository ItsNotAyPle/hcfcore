package org.ayple.hcfcore.core.cooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.faction.Faction;
import org.bukkit.scheduler.BukkitRunnable;

public class DtrRegenTimer  extends BukkitRunnable {
    private Faction owner;
    public int seconds_left;
    public int getSecondsLeft() { return this.seconds_left; }

    public DtrRegenTimer(Faction owner) {
        this.owner = owner;
        if (Hcfcore.getInstance().KITMAP_MODE) {
            this.seconds_left = 60;
        } else {
            this.seconds_left = 3600;
        }
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        if (seconds_left == 0) {
            CooldownManager.onFinishedDtrRegen(owner);
            cancel();
        } else {
            seconds_left--;
        }
    }
}