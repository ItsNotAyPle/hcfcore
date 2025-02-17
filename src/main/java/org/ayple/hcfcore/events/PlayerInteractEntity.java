package org.ayple.hcfcore.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractEntity implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            player.sendMessage("You can't interact with villagers");
            event.setCancelled(true);
        }

    }
}
