package org.ayple.hcfcore.events;

import org.ayple.hcfcore.kits.kitmap.KitmapManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
//import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerArmorChangeEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (player != null) {
            if (!KitmapManager.wearingAllArmor(player.getInventory())) {
                removeAllSpecialEffects(player);
                return;
            }

            PlayerInventory inventory = player.getInventory();
            ItemStack helmet = inventory.getHelmet();
            ItemStack chestplate = inventory.getChestplate();
            ItemStack leggings = inventory.getLeggings();
            ItemStack boots = inventory.getBoots();




            if (helmet.getType() == Material.IRON_HELMET &&
                chestplate.getType() == Material.IRON_CHESTPLATE &&
                leggings.getType() == Material.IRON_LEGGINGS &&
                boots.getType() == Material.IRON_BOOTS)
            {
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false));
                return;
            } else if (helmet.getType() == Material.LEATHER_HELMET &&
                    chestplate.getType() == Material.LEATHER_CHESTPLATE &&
                    leggings.getType() == Material.LEATHER_LEGGINGS &&
                    boots.getType() == Material.LEATHER_BOOTS)
            {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false));
                return;
            } else if (helmet.getType() == Material.GOLD_HELMET &&
                    chestplate.getType() == Material.GOLD_CHESTPLATE &&
                    leggings.getType() == Material.GOLD_LEGGINGS &&
                    boots.getType() == Material.GOLD_BOOTS)
            {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0, false));
                return;
            }

            removeAllSpecialEffects(player);

        }


    }

    // move to a utils class


    private void removeAllSpecialEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getDuration() > 10000) {
                player.removePotionEffect(effect.getType());
            }
        }
    }
}
