package org.ayple.hcfcore.core.cooldowns.newcooldowns;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class LogoutTimer extends AbstractCooldown {
    public LogoutTimer(Player owner) {
        super(owner, 15, "Logout Timer");
    }

    @Override
    public void onTimerFinished() {
        CooldownManager.onLogoutTimerDone(this.ownerID);
        OfflinePlayer target_player = Bukkit.getOfflinePlayer(ownerID);
        if (target_player.isOnline()) {
            target_player.getPlayer().kickPlayer("Logged out safely.");
        }
    }

    @Override
    public void onTimerChanged() {

    }
}