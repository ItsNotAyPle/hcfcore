package org.ayple.hcfcore.core.cooldowns.newcooldowns;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.entity.Player;

public class PvpTimer extends AbstractCooldown {
    public PvpTimer(Player owner) {
        super(owner, 1800, "PVP Timer");
    }

    @Override
    public void onTimerFinished() {
        CooldownManager.onFinishedPvpTimer(this.ownerID);
    }

    @Override
    public void onTimerChanged() {

    }
}
