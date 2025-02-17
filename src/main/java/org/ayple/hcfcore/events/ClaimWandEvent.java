package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.BalanceHandler;
import org.ayple.hcfcore.core.claims.*;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.core.items.ClaimWand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;


// TODO: Make sure no other claims are in cuboid region
public class ClaimWandEvent implements Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // only allow admins to claim in overworld (for koths and glowstone mountain)
        // is the player holding a claim wand
        // are they in a faction? if not then return
        if (player.getWorld() != Bukkit.getWorld("world") && !player.hasPermission("hcfcore.admin")) return;
        if (!ClaimWand.isItemClaimWand(player.getInventory().getItemInHand())) return;
        if (!checkPlayerInFaction(player)) {
            player.sendMessage(ChatColor.RED + "You are not in a faction!");
            return;
        }
        if (NewFactionManager.getFactionFromPlayerID(player.getUniqueId()).getClaim() != null) {
            player.sendMessage(ChatColor.RED + "You already have a claim!");
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Location pos = event.getClickedBlock().getLocation();
            if (ClaimsManager.blockInClaim(pos)) {
                player.sendMessage(ChatColor.RED + "This block is already claimed!");
                return;
            }

            if (!checkBlockInClaimArea(pos)) {
                player.sendMessage(ChatColor.RED + "You need to go 750 blocks out to claim!");
                return;
            }

            ClaimPillarManager.addCorner1(player, (int) pos.getX(), (int) pos.getZ());
            player.sendMessage(ChatColor.GREEN + "Set first position!");
            event.setCancelled(true);

        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Location pos = event.getClickedBlock().getLocation();
            if (ClaimsManager.blockInClaim(pos)) {
                player.sendMessage(ChatColor.RED + "This block is already claimed!");
                return;
            }

            if (!checkBlockInClaimArea(pos)) {
                player.sendMessage(ChatColor.RED + "You need to go 750 blocks out to claim!");
                return;
            }

            ClaimPillarManager.addCorner2(player, pos.getX(), pos.getZ());
            player.sendMessage(ChatColor.GREEN + "Set second position!");
            event.setCancelled(true);

        }

        if (event.getAction() == Action.LEFT_CLICK_AIR && player.isSneaking()) {
                Faction faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());

                if (faction == null) {
                    player.sendMessage(ChatColor.RED + "You are not in a faction");
                    return;
                }

                if (faction.getClaim() != null) {
                    player.sendMessage(ChatColor.RED + "Your faction already has a claim!");
                    return;
                }


                Location corner_1 = ClaimPillarManager.getFirstCornerAsLocation(player.getUniqueId());
                Location corner_2 = ClaimPillarManager.getSecondCornerAsLocation(player.getUniqueId());
                if (corner_1 == null) {
                    player.sendMessage(ChatColor.RED + "First corner isn't set!");
                    return;
                }

                if (corner_2 == null) {
                    player.sendMessage(ChatColor.RED + "Second corner isn't set!");
                    return;
                }


                // make claim
                Selection selection = new Selection(corner_1, corner_2);
                if (!ClaimsManager.isClaimSizeLegal(selection.getCuboid())) {
                    player.sendMessage(ChatColor.RED + "Claim must be larger than a 5x5 and smaller than a 76x76!");
                    return;
                }

                if (ClaimsManager.otherClaimInCuboid(selection.getCuboid())) {
                    player.sendMessage(ChatColor.RED + "Claim is already in the region!");
                    return;
                }



                int price = ClaimsManager.getClaimSizePrice(selection.getCuboid());
                System.out.println("Price :" + price);
                if (faction.getFactionBal() < price) {
                    player.sendMessage(ChatColor.RED + "Insufficient funds in faction balance. Claim cost: " + ChatColor.GOLD + Integer.toString(price));
                    return;
                }



                // TODO: look into this as i feel it may cause errors - 16/07/23
                Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
                    try {
                        ClaimsManager.newClaim(player, selection, price);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                // clear wand
                removeClaimWand(player);

                // clear pillars
                // Only commented out for debug purposes
                ClaimPillarManager.removeCorner1Pillar(player);
                ClaimPillarManager.removeCorner2Pillar(player);

                player.sendMessage(ChatColor.GREEN + "Claimed land!");
                event.setCancelled(true);

        }




        // LEGACY CODE AS OF 09/07/23
//        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
//            System.out.println("Left clicked floor with claim wand!");
//            Location pos1 = event.getClickedBlock().getLocation();
//            Selection old_selection = SelectionsManager.getAnyExistingSelection(player.getUniqueId());
//            if (old_selection != null) {
//                if (old_selection.getPos1() != null) {
//                    removePillar(world, player, (int) old_selection.getPos1().getX(), (int) old_selection.getPos1().getZ());
//                }
//            }
//
//            SelectionsManager.setPos1(player, pos1);
//            placePillar(world, player, (int) pos1.getX(), (int) pos1.getZ());
//        }
//
//        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
//            System.out.println("right clicked floor with claim wand!");
//            Location pos2 = event.getClickedBlock().getLocation();
//            Selection old_selection = SelectionsManager.getAnyExistingSelection(player.getUniqueId());
//            if (old_selection != null) {
//                if (old_selection.getPos1() != null) {
//                    removePillar(world, player, (int) old_selection.getPos1().getX(), (int) old_selection.getPos1().getZ());
//                }
//            }
//
//            SelectionsManager.setPos2(player, pos2);
//            placePillar(world, player, (int) pos2.getX(), (int) pos2.getZ());
//        }
//
//        if (event.getAction() == Action.LEFT_CLICK_AIR && player.isSneaking()) {
//            try {
//                Selection selection = SelectionsManager.getSelection(player);
//                if (selection.getPos1() == null) {
//                    player.sendMessage("First corner isn't set!");
//                    return;
//                }
//
//                if (selection.getPos2() == null) {
//                    player.sendMessage("Second corner isn't set!");
//                    return;
//                }
//
//                // make claim
//                ClaimsManager.newClaim(player, selection);
//
//                // clear wand
//                removeClaimWand(player);
//
//                // clear pillars
//                removePillar(world, player, (int) selection.getPos1().getX(), (int) selection.getPos1().getZ());
//                removePillar(world, player, (int) selection.getPos2().getX(), (int) selection.getPos2().getZ());
//
//                player.sendMessage("Claimed land!");
//            } catch(SQLException e) {
//                 player.sendMessage("SQL Error making claim! Consult developer immediately.");
//                e.printStackTrace();
//                return;
//            }
//
//        }






    }

    // TODO: this kinda works but good enough for now ngl
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        if (ClaimWand.isItemClaimWand(item)) {
            if (event.getWhoClicked() instanceof Player) {
//                removeClaimWand((Player) event.getWhoClicked());
                event.setCurrentItem(null);
            }
        }
    }



    public void removeClaimWand(Player player) {
        player.getInventory().removeItem(ClaimWand.makeNewWand());
    }

    public void removeClaimWand(Player player, ItemStack wand) {
        player.getInventory().removeItem(wand);
    }


    public boolean checkPlayerInFaction(Player player) {

        if (!NewFactionManager.playerInFaction(player.getUniqueId())) {
            removeClaimWand(player);
            player.sendMessage("You are not in a faction!");
            return false;
        }


        return true;
    }

    private static boolean checkBlockInClaimArea(Location location) {
//        System.out.println("X greater than or equal to 750: " + (location.getX() >= 750));
//        System.out.println("X less than or equal to -750: " + (location.getX() <= -750));
//        System.out.println("X greater than or equal to 750: " + (location.getZ() >= 750));
//        System.out.println("X less than or equal to -750: " + (location.getZ() <= -750));
        return (location.getX() >= 750 || location.getX() <= -750) || (location.getZ() <= -750 || location.getZ() >= 750);
    }
}
