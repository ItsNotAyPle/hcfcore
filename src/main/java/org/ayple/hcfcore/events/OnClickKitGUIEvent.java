package org.ayple.hcfcore.events;

import org.ayple.hcfcore.kits.kitmap.KitmapManager;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


// TODO: add kit cooldowns
// theres a high chance these kits will never be used due to
// the nature of the server of not being pay to win and
// signs will be available in spawn for kitmap.
public class OnClickKitGUIEvent implements Listener {

//    @EventHandler
//    public void clickEvent(InventoryClickEvent e) {
//        if (e.getClickedInventory() == null) {
//            return;
//        }
//
//        if (e.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Kits Menu")) {
//            Player player = (Player) e.getWhoClicked();
//
//            if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
//                if (e.getCurrentItem().getData().getData() == DyeColor.LIGHT_BLUE.getData()) {
//                    if (!player.hasPermission("hcf.kit.diamond")) {
//                        player.sendMessage(ChatColor.RED + "You don't have permission!");
//                        return;
//                    }
//
//                    player.closeInventory();
//                    KitmapManager.diamondkit.givePlayerKit(((Player) e.getWhoClicked()));
//                }
//                else if (e.getCurrentItem().getData().getData() == DyeColor.YELLOW.getData()) {
//                    if (!player.hasPermission("hcf.kit.bard")) {
//                        player.sendMessage(ChatColor.RED + "You don't have permission!");
//                        return;
//                    }
//
//                    player.closeInventory();
////                    KitManager.bardkit.givePlayerKit(((Player) e.getWhoClicked()));
//                }
//                else if (e.getCurrentItem().getData().getData() == DyeColor.PURPLE.getData()) {
//                    if (!player.hasPermission("hcf.kit.archer")) {
//                        player.sendMessage(ChatColor.RED + "You don't have permission!");
//                        return;
//                    }
//
//                    player.closeInventory();
//                    KitmapManager.archerkit.givePlayerKit(((Player) e.getWhoClicked()));
//                } else if (e.getCurrentItem().getData().getData() == DyeColor.WHITE.getData()) {
//                    if (!player.hasPermission("hcf.kit.miner")) {
//                        player.sendMessage(ChatColor.RED + "You don't have permission!");
//                        return;
//                    }
//
//                    player.closeInventory();
////                    KitmapKit.minerkit.givePlayerKit(((Player) e.getWhoClicked()));
//                }
//            }
//
//            e.setCancelled(true);
//        }
//    }
}
