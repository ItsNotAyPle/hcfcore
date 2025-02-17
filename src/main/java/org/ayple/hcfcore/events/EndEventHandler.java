package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.*;

public class EndEventHandler implements Listener {

    private final Location end_portal_exit = new Location(
            Bukkit.getWorld("world"),
            ConfigHelper.getConfig().getDouble("end_portal_exit.x"),
            ConfigHelper.getConfig().getDouble("end_portal_exit.y"),
            ConfigHelper.getConfig().getDouble("end_portal_exit.z")
    );


    private final Location end_portal_entry = new Location(
            Bukkit.getWorld("world"),
            -50,
            62,
            -50
    );

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            if (event.getTo().getWorld().getEnvironment() == World.Environment.NORMAL) {
                System.out.println("Player has exited end!");
//                player.teleport(end_portal_exit);
                event.setTo(end_portal_exit);

            } else if (event.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {
                System.out.println("Entering portal!");
                if (CooldownManager.playerHasPvpTimer(event.getPlayer().getUniqueId())) {
                    event.getPlayer().sendMessage(ChatColor.RED + "You cannot enter the end with Pvp Timer!");
                    event.setCancelled(true);
                    return;
                }

//                event.getPlayer().teleport(end_portal_entry);
//                event.setTo(end_portal_entry);
                event.useTravelAgent(false);

//                Location destination = end_portal_entry.clone();
//                destination.setY(destination.getY() - 1); // Go down one block to replace the platform
//                destination.getBlock().setType(Material.AIR);

            }
        }
    }



    // TODO: need to take this out of end event listener
    @EventHandler
    public void onCreatureSpawn(EntityTargetEvent event) {
        if (event.getEntityType().equals(EntityType.CREEPER) && event.getTarget() instanceof Player){
            event.setCancelled(true);
        }
    }

//    @EventHandler(ignoreCancelled = true)
//    public void onMove(PlayerMoveEvent event) {
//        Location from = event.getFrom();
//        Location to = event.getTo();
//
//        if (from.getWorld().getEnvironment() != World.Environment.THE_END || to.getWorld().getEnvironment() != World.Environment.THE_END)
//            return;
//
//        if (to.getBlockY() < -128) {
//            event.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
//        }
//    }



    // this isn't needed since the warzone thing stops it lmfao
//    @EventHandler
//    public void onPlayerInteractInEnd(PlayerInteractEvent event) {
//        if (event.getPlayer().getWorld() != Bukkit.getWorld("end")) return;
//
//        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
//            if (event.getPlayer().hasPermission("hcf.core.interact_in_end")) {
//                return;
//            }
//
//            event.setCancelled(true);
//            event.getPlayer().sendMessage(ChatColor.RED + "You cannot interact in end!");
//        }
//    }











}
