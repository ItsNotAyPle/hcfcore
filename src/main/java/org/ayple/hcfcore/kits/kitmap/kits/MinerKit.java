package org.ayple.hcfcore.kits.kitmap.kits;

import org.ayple.hcfcore.kits.kitmap.KitmapKit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MinerKit extends KitmapKit {
    @Override
    public String getKitName() {
        return "Miner";
    }

    @Override
    public ItemStack[] getKitArmor() {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);

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
        ItemStack pickaxe1 = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack pickaxe2 = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 64);
        ItemStack pearls = new ItemStack(Material.ENDER_PEARL, 16);

        pickaxe1.addEnchantment(Enchantment.DIG_SPEED, 5);
        pickaxe1.addEnchantment(Enchantment.DURABILITY, 3);
        pickaxe2.addEnchantment(Enchantment.DIG_SPEED, 5);
        pickaxe2.addEnchantment(Enchantment.DURABILITY, 3);
        pickaxe2.addEnchantment(Enchantment.SILK_TOUCH, 1);


        inv.addItem(pickaxe1);
        inv.addItem(pickaxe2);
        inv.addItem(new ItemStack(Material.WOOL, 64));
        inv.addItem(new ItemStack(Material.WOOL, 64));
        inv.addItem(new ItemStack(Material.BRICK, 64));
        inv.addItem(new ItemStack(Material.STONE, 64));
        inv.addItem(new ItemStack(Material.COBBLESTONE, 64));
        inv.addItem(new ItemStack(Material.GLOWSTONE, 64));
        inv.addItem(new ItemStack(Material.SMOOTH_BRICK, 64));
        inv.addItem(new ItemStack(Material.SMOOTH_BRICK, 64));
        inv.addItem(new ItemStack(Material.SMOOTH_BRICK, 64));
        inv.addItem(new ItemStack(Material.FENCE_GATE, 64));
        inv.addItem(new ItemStack(Material.LOG, 64));
        inv.addItem(new ItemStack(Material.GRASS, 64));



        inv.setItem(0, pickaxe1);
        inv.setItem(1, pickaxe2);
        inv.setItem(2, pearls);
        inv.setItem(9, steak);

        return inv;

    }
}
