package org.ayple.hcfcore.core.cooldowns.newcooldowns;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class EnderpearlCooldown extends AbstractCooldown {

    public EnderpearlCooldown(Player owner) {
        super(owner, 30, "Enderpearl Cooldown");
    }

    @Override
    public void onTimerFinished() {
        CooldownManager.onFinishedEnderpearlCooldown(this.ownerID);
    }

    @Override
    public void onTimerChanged() {

    }
}
