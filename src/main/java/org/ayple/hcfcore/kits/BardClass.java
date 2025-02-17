package org.ayple.hcfcore.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BardClass {

    public static void giveEffectToTargetPlayer(Material item_bard_holding, Player target_player) {
        if (item_bard_holding == Material.SUGAR) {
            target_player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1, true));
        } else if (item_bard_holding == Material.BLAZE_POWDER) {
            target_player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0, true));
        } else if (item_bard_holding == Material.MAGMA_CREAM) {
            target_player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 0, true));
        } else if (item_bard_holding == Material.GOLDEN_CARROT) {
            target_player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 0, true));
        } else if (item_bard_holding == Material.IRON_INGOT) {
            target_player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0, true));
        }
    }

    // when bard right clicks on sugar give speed 3 instead of speed 2 (for example)
    public static boolean giveSpecialEffectToPlayer(Material item_bard_holding, Player target_player) {
        if (item_bard_holding == Material.SUGAR) {
            target_player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 2, true));
            return true;
        } else if (item_bard_holding == Material.BLAZE_POWDER) {
            target_player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 140, 1, true));
            return true;
        } else if (item_bard_holding == Material.IRON_INGOT) {
            target_player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 140, 1, true));
            return true;
        }

        return false;
    }

    public static boolean playerWearingBard(PlayerInventory inventory) {
        ItemStack helmet = inventory.getHelmet();
        ItemStack chestplate = inventory.getChestplate();
        ItemStack leggings = inventory.getLeggings();
        ItemStack boots = inventory.getBoots();

        return (helmet.getType() == Material.GOLD_HELMET &&
                chestplate.getType() == Material.GOLD_CHESTPLATE &&
                leggings.getType() == Material.GOLD_LEGGINGS &&
                boots.getType() == Material.GOLD_BOOTS
        );
    }
}
