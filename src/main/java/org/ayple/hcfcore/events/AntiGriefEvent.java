package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

//        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
//            Material item_player_holding = Objects.requireNonNull(event.getPlayer().getItemInUse().getType());
//            Material clicked_block = event.getClickedBlock().getType();
//
//            if (item_player_holding.equals(Material.ENDER_PEARL)) {
//                //if (event.getClickedBlock())
//            }
//        }

//            Material item_in_use = player.getItemInUse().getType();
//            if (item_in_use == null) {
//                return;
//            }
//
//            if (item_in_use.equals(Material.ENDER_PEARL)) {
//                if (!CooldownManager.hasEnderpearlCooldown(player.getUniqueId())) {
//                    CooldownManager.registerEnderpearlCooldown(player.getUniqueId());
//                } else {
//                    player.sendMessage("You currently have an enderpearl timer!");
//                }
//
//            }

public class AntiGriefEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if ((player.isOp() || player.hasPermission("hcf.core.griefbypass")) && player.getGameMode() == GameMode.CREATIVE) return;

        World player_world = player.getWorld();
//        boolean player_in_nether = player_world.getEnvironment().equals(World.Environment.NETHER);
        boolean player_in_end = player_world.getEnvironment().equals(World.Environment.THE_END);
//        System.out.println("player in nether: " + player_in_nether);
//        System.out.println("player in end: " + player_in_end);
//        if (player_in_nether) return;
//        if (player_in_end && player.hasPermission("hcf.core.edit_in_end")) return;






        // gets claim players in
        Claim claim = ClaimsManager.getClaimPlayerIn(player);


        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() instanceof Sign) {
                return;
            }

            if (player.getInventory().getItemInHand() != null) {
                if (player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL) {
                    return;
                }

                if (player.getInventory().getItemInHand().getType() == Material.POTION) {
                    return;
                }

                if (player.getInventory().getItemInHand().getType().isEdible()) {
                    return;
                }
            }




            if (event.getClickedBlock() != null) {

                // players in wilderness
                if (claim == null)  {
                    if (ClaimsManager.playerInWarzone(player)) {
                        player.sendMessage(ChatColor.RED + "You cannot interact in Warzone!");
                        event.setCancelled(true);
                    }

                    return;
                }


                if (claim.isClaimSpawn()) {
                    player.sendMessage(ChatColor.RED + "You cannot interact in Spawn!");
                    event.setCancelled(true);
                    return;
                }

                // commented this out since it turns out i already did this
//                if (ClaimsManager.playerInWarzone(player)) {
//                    if (player.hasPermission("hcf.core.edit_warzone")) {
//                        return;
//                    }
//
//                    player.sendMessage(ChatColor.RED + "You cannot build or break in warzone!");
//                    event.setCancelled(true);
//                    return;
//                }

                // if player in claim
                // if (claim_owner.getClaim().getOwnerFactionID())
                if (!ClaimsManager.playerOwnsClaimTheyreIn(player) && claim.getOwnerFaction().getFactionDTR() > 0) {
                    player.sendMessage(ChatColor.RED + "You can't interact in other peoples claims!");
                    event.setCancelled(true);
                }

            }
        }




    }

}
