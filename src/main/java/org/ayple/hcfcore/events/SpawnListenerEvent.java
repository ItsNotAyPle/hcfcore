package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpawnListenerEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event == null || event.getPlayer() == null) return; // ?? why did i write this

        Claim claim = ClaimsManager.getClaimPlayerIn(event.getPlayer());
        if (claim == null) {
            // usually in wilderness or something idk. should be fine
            return;
        }

        if (claim.isClaimSpawn()) {
            if (CooldownManager.hasCombatTimer(event.getPlayer().getUniqueId())) {
                // https://www.spigotmc.org/threads/disable-walking-into-a-region.296326/
                event.getPlayer().setVelocity(event.getTo().toVector().subtract(event.getFrom().toVector()).normalize().multiply(10));
                event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed in spawn with a combat timer!");
            }
        }
    }
}
