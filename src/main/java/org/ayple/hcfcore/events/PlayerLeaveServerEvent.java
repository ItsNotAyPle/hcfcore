package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.claims.SelectionsManager;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.cooldowns.newcooldowns.LogoutVillagerTimer;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class PlayerLeaveServerEvent implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        Hcfcore.getInstance().getScoreboardHandler().onPlayerQuitServer(event.getPlayer());
        CooldownManager.cancelHomeTimer(p.getUniqueId());
        SelectionsManager.clearAnySelectionPlayerHas(p);
        CooldownManager.registerLogoutVillager(p);
    }
}
