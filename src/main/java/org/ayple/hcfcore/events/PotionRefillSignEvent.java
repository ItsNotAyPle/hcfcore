package org.ayple.hcfcore.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PotionRefillSignEvent implements Listener {
    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("hcf.createpotsign")) return;

        if (e.getLine(0).equalsIgnoreCase("[Refill]")) {
            e.setLine(0, ChatColor.GOLD + "- POTION REFILL -");
            e.setLine(1, ChatColor.GREEN + "RIGHT CLICK");
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getClickedBlock().getState() instanceof Sign) {
            Sign sign = (Sign) e.getClickedBlock().getState();
            if (sign.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "RIGHT CLICK")) {
                player.openInventory(getPotionRefillMenu());
            }

        }
    }

    public static Inventory getPotionRefillMenu() {
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.DARK_PURPLE + "Potions");
        ItemStack potion = new ItemStack(Material.POTION, 1, (short) 16421);

        for (int i = 0; i < gui.getSize(); i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, potion);
            }
        }

        return gui;
    }
}
