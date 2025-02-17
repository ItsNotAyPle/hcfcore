package org.ayple.hcfcore.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ArcherClass {

    public static boolean playerWearingArcher(PlayerInventory inventory) {
        ItemStack helmet = inventory.getHelmet();
        ItemStack chestplate = inventory.getChestplate();
        ItemStack leggings = inventory.getLeggings();
        ItemStack boots = inventory.getBoots();

        return (helmet.getType() == Material.LEATHER_HELMET &&
                chestplate.getType() == Material.LEATHER_CHESTPLATE &&
                leggings.getType() == Material.LEATHER_LEGGINGS &&
                boots.getType() == Material.LEATHER_BOOTS
        );
    }
}
