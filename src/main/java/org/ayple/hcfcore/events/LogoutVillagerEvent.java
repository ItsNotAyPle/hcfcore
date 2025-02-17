package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;
import java.util.UUID;

public class LogoutVillagerEvent implements Listener {
    @EventHandler
    public void onVillagerDeath(EntityDeathEvent event) {
        World world = event.getEntity().getWorld();
        if (event.getEntityType() == EntityType.VILLAGER) {
            Villager villager = (Villager) event.getEntity();
            if (event.getEntity().hasMetadata("LOGOUT VILLAGER")) {
                // if this is true then god has touched the plugin
                if (!villager.hasMetadata("OWNER UUID")) {
                    System.out.println("Villager doesn't have owner uuid?");
                    return;
                }

                List<MetadataValue> values = villager.getMetadata("OWNER UUID");
                String playeruuid = values.get(0).asString();
                UUID player_id = UUID.fromString(playeruuid);

                if (!CooldownManager.hasLogoutVillager(player_id)) {
                    return;
                }

                CooldownManager.addDeadLogoutVillager(player_id);
                Location dropLocation = event.getEntity().getLocation();

                CooldownManager.getLogoutVillagerInventoryData(player_id).forEach((itemStack -> {
                    if (itemStack != null) {
                        world.dropItem(dropLocation, itemStack);
                    }
                }));

                PlayerDataHandler.increasePlayerKillCount(player_id);

            }
        }
    }

    @EventHandler
    public void onVillagerTarget(EntityTargetEvent event) {
        if (event.getEntity() instanceof Villager) {
            event.setCancelled(true);
        }

        if (event.getTarget() instanceof Villager) {
            event.setCancelled(true);
        }
    }



    @EventHandler
    public void onVillagerTeleport(EntityTeleportEvent event) {
        if (event.getEntity() instanceof Villager) {
            event.setCancelled(true);
        }
    }
}
