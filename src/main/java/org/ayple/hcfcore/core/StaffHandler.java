package org.ayple.hcfcore.core;

import org.ayple.hcfcore.Hcfcore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaffHandler {
    private static final HashMap<UUID, PlayerInventory> staffMembersInStaffMode = new HashMap<UUID, PlayerInventory>();

    public static Inventory getStaffInventory() {
        Inventory staffinv = Bukkit.createInventory(null, InventoryType.PLAYER);

        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compass_meta = compass.getItemMeta();
        compass_meta.setDisplayName(ChatColor.RED + "Phase Compass");
        compass.setItemMeta(compass_meta);

        staffinv.setItem(0, compass);

        return staffinv;
    }

    public static void onEnterStaffMode(Player player) {
        player.setMetadata("STAFF_MODE", new FixedMetadataValue(Hcfcore.getInstance(), "yes"));
        staffMembersInStaffMode.put(player.getUniqueId(), player.getInventory());
        player.getInventory().clear();
        player.getInventory().setContents(getStaffInventory().getContents());
        player.updateInventory();

        player.setGameMode(GameMode.CREATIVE);
        player.performCommand("god");
        player.performCommand("vanish");

    }

    public static void onExitStaffMode(Player player) {
        player.removeMetadata("STAFF_MODE", Hcfcore.getInstance());
        player.getInventory().clear();
        player.getInventory().setContents(staffMembersInStaffMode.get(player.getUniqueId()).getContents());
        player.updateInventory();

        player.setGameMode(GameMode.SURVIVAL);
        player.performCommand("god");
        player.performCommand("vanish");

        staffMembersInStaffMode.remove(player.getUniqueId());
    }

    public static boolean playerInStaffMode(Player player) {
        if (staffMembersInStaffMode.containsKey(player.getUniqueId())) {
            if (player.hasMetadata("STAFF_MODE")) {
                return true;
            }

            player.sendMessage(ChatColor.RED + "Try running it again. In array but no meta data? what. if this happens then ayple is a shit programmer");
            staffMembersInStaffMode.remove(player.getUniqueId());
        }

        return false;
    }

}
