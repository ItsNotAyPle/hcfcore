package org.ayple.hcfcore.core.cooldowns.newcooldowns;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class HomeTimer extends AbstractCooldown {

    Location hq;

    public HomeTimer(Player owner, Location hq) {
        super(owner, 20, "Home Timer");
        this.hq = hq;
    }

    @Override
    public void onTimerFinished() {
        CooldownManager.onFinishedHomeTimer(this.ownerID);
        OfflinePlayer target_player = Bukkit.getOfflinePlayer(ownerID);
        if (target_player.isOnline()) {
            target_player.getPlayer().teleport(this.hq);
        }
    }

    @Override
    public void onTimerChanged() {

    }
}
