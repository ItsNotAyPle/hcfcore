package org.ayple.hcfcore.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;

import java.util.Map;

public class EnchantLimiterEvent implements Listener {
//    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
//    public void onPlayerEnchantItem(EnchantItemEvent event) {
//        final Player player = event.getEnchanter();
//        ItemStack item = event.getItem();
//
//        if (item == null || item.getType() == Material.AIR || !item.getItemMeta().hasEnchant(Enchantment.LUCK)) {
//            // Check if the enchantment is Protection or Sharpness
//            if (event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) ||
//                    event.getEnchantsToAdd().containsKey(Enchantment.DAMAGE_ALL)) {
//                // Get the current enchantment level for Protection and Sharpness
//                int protectionLevel = event.getEnchantsToAdd().get(Enchantment.PROTECTION_ENVIRONMENTAL);
//                int sharpnessLevel = event.getEnchantsToAdd().get(Enchantment.DAMAGE_ALL);
//
//                // Set the desired limits (Protection 2 and Sharpness 2)
//                if (protectionLevel > 2) {
//                    event.getEnchantsToAdd().put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
//                    player.sendMessage(ChatColor.GREEN + "Set item to protection 2!");
//                }
//                if (sharpnessLevel > 2) {
//                    event.getEnchantsToAdd().put(Enchantment.DAMAGE_ALL, 2);
//                    player.sendMessage(ChatColor.GREEN + "Set item to sharpness 2!");
//                }
//            }
//        }
//    }

//    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
//    public void onEntityDamage(EntityDamageEvent event) {
//        if (event.getEntity() instanceof Player) {
//
//        }
//    }
//
//    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
//    public void onEntityDamagebyEntity(EntityDamageEvent event) {
//
//    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        Map<Enchantment, Integer> enchantments = item.getEnchantments();

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            if (item.getEnchantments() != null) {
                if (enchantments.containsKey(Enchantment.ARROW_DAMAGE)) {
                    if (enchantments.get(Enchantment.ARROW_DAMAGE) > 4) {
                        item.addEnchantment(Enchantment.ARROW_DAMAGE, 4);
                        player.sendMessage(ChatColor.RED + "Illegal enchantment fount. Setting power to 4!");
                    }
                }

                if (enchantments.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    if (enchantments.get(Enchantment.PROTECTION_ENVIRONMENTAL) > 2) {
                        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        player.sendMessage(ChatColor.RED + "Illegal enchantment fount. Setting protection to 2!");
                    }
                }

                if (enchantments.containsKey(Enchantment.DAMAGE_ALL)) {
                    if (enchantments.get(Enchantment.DAMAGE_ALL) > 2) {
                        item.addEnchantment(Enchantment.DAMAGE_ALL, 2);
                        player.sendMessage(ChatColor.RED + "Illegal enchantment fount. Setting sharpness to 2!");
                    }
                }

                if (enchantments.containsKey(Enchantment.FIRE_ASPECT)) {
                    if (enchantments.get(Enchantment.FIRE_ASPECT) > 2) {
                        item.removeEnchantment(Enchantment.FIRE_ASPECT);
                        player.sendMessage(ChatColor.RED + "Illegal enchantment fount. Removing fire aspect!");
                    }
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();



        if (player.getItemInHand().getEnchantments() != null) {
            Map<Enchantment, Integer> enchantments = item.getEnchantments();

            if (enchantments.containsKey(Enchantment.ARROW_DAMAGE)) {
                if (enchantments.get(Enchantment.ARROW_DAMAGE) > 4) {
                    item.addEnchantment(Enchantment.ARROW_DAMAGE, 4);
                    player.sendMessage(ChatColor.RED + "Illegal enchantment fount. Setting protection to 4!");
                }
            }

            if (enchantments.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                if (enchantments.get(Enchantment.PROTECTION_ENVIRONMENTAL) > 2) {
                    item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                    player.sendMessage(ChatColor.RED + "Illegal enchantment fount. Setting protection to 2!");
                }
            }

            if (enchantments.containsKey(Enchantment.DAMAGE_ALL)) {
                if (enchantments.get(Enchantment.DAMAGE_ALL) > 2) {
                    item.addEnchantment(Enchantment.DAMAGE_ALL, 2);
                    player.sendMessage(ChatColor.RED + "Illegal enchantment fount. Setting sharpness to 2!");
                }
            }
        }



    }

    public void checkArmor(Player player) {

    }

    public void checkSword(Player player) {

    }




}
