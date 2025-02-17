package org.ayple.hcfcore.kits.kitmap;


import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public abstract class KitmapKit {
    public abstract String getKitName();
    public abstract ItemStack[] getKitArmor();
    public abstract Inventory getKitInventory();

    public void givePlayerKit(Player player) {
        PlayerInventory inventory = player.getInventory();


        // if in kitmap mode, clear inventory and wear armor
        // else just give them the armor
        if (Hcfcore.getInstance().KITMAP_MODE) {
            inventory.clear();
            inventory.setContents(inventory.getContents());
            inventory.setArmorContents(getKitArmor());
        } else {
            inventory.addItem(getKitArmor());
            inventory.addItem(getKitInventory().getContents());
        }
    }
}