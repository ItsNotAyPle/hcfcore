package org.ayple.hcfcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;



public class TntDisablerEvent implements Listener {
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
//        // Check if the entity involved in the explosion is TNT
//        if (event.getEntityType().toString().equalsIgnoreCase("TNT")) {
//            // Cancel the explosion
//            event.setCancelled(true);
//        }

        event.setCancelled(true);

    }
}
