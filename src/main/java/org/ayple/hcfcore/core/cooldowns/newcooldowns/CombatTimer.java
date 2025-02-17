package org.ayple.hcfcore.core.cooldowns.newcooldowns;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.entity.Player;

public class CombatTimer extends AbstractCooldown {

    public CombatTimer(Player owner) {
        super(owner, 30, "combat_timer");
    }

    @Override
    public void onTimerFinished() {
        CooldownManager.onCombatTimerOver(this.ownerID);
    }

    @Override
    public void onTimerChanged() {

    }
}
