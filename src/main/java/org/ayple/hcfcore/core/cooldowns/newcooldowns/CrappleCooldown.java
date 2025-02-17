package org.ayple.hcfcore.core.cooldowns.newcooldowns;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CrappleCooldown extends AbstractCooldown {
    public CrappleCooldown(Player owner) {
        super(owner, 30, "crapple_timer");
    }

    @Override
    public void onTimerFinished() {
        CooldownManager.onFinishedCrappleCooldown(this.ownerID);
    }

    @Override
    public void onTimerChanged() {

    }
}
