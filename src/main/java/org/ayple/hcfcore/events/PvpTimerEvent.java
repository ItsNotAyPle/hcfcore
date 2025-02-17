package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PvpTimerEvent implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(Bukkit.getWorld("world").getSpawnLocation());
        if (!Hcfcore.getInstance().KITMAP_MODE) {
            CooldownManager.registerPvpTimer(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event == null || event.getPlayer() == null) return; // ?? why did i write this
        Player p = event.getPlayer();

        if (CooldownManager.playerHasPvpTimer(p.getUniqueId())) {
            if (ClaimsManager.getClaimPlayerIn(p) != null) {
                event.getPlayer().setVelocity(event.getTo().toVector().subtract(event.getFrom().toVector()).normalize().multiply(10));
                event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed in claims with a pvp timer!");
            }
        }
    }
}
