package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.Cuboid;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.claims.map.ClaimMap;
import org.ayple.hcfcore.core.claims.map.MapPillarsHandler;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMoveEvent implements Listener {
//    public Hashtable<UUID, Location> player_last_location = new Hashtable<UUID, Location>();

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event == null || event.getPlayer() == null) return;
        Player player = event.getPlayer();


        if (event.getFrom().getX() == event.getTo().getX() && event.getFrom().getZ() == event.getTo().getZ()) return;

        // cancel home timer if they move
        if (CooldownManager.hasHomeTimer(player.getUniqueId())) {
            CooldownManager.cancelHomeTimer(player.getUniqueId());
        }

        if (CooldownManager.hasLogoutTimer(player.getUniqueId())) {
            CooldownManager.cancelLogoutTimer(player.getUniqueId());
        }

        ClaimsManager.relayPlayerInClaim(player);

    }

}
