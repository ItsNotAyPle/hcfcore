package org.ayple.hcfcore.kits.kitmap.kits;

import org.ayple.hcfcore.kits.kitmap.KitmapKit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class BardKit extends KitmapKit {

    @Override
    public String getKitName() {
        return "Diamond";
    }

    @Override
    public ItemStack[] getKitArmor() {
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
        ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS);
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);

        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

        helmet.addEnchantment(Enchantment.DURABILITY, 3);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        boots.addEnchantment(Enchantment.DURABILITY, 3);
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 4);

        return new ItemStack[]{helmet, chestplate, leggings, boots};
    }

    @Override
    public Inventory getKitInventory() {

        Inventory inv = Bukkit.createInventory(null, 36);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 64);
        ItemStack pearls = new ItemStack(Material.ENDER_PEARL, 16);

        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        sword.addEnchantment(Enchantment.LOOT_BONUS_MOBS, 3);

        inv.addItem(sword);
        inv.addItem(new ItemStack(Material.SUGAR, 64));
        inv.addItem(new ItemStack(Material.BLAZE_POWDER, 64));
        inv.addItem(new ItemStack(Material.MAGMA_CREAM, 64));
        inv.addItem(new ItemStack(Material.IRON_INGOT, 64));


        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack healing_potion = new ItemStack(Material.POTION, 1, (short) 16421);

            if (inv.getItem(i) == null) {
                inv.setItem(i, healing_potion);
            }
        }

        inv.setItem(0, sword);
        inv.setItem(1, pearls);
        inv.setItem(9, steak);

        return inv;

    }

}
