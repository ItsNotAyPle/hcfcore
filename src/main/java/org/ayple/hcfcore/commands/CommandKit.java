package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.Hcfcore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandKit implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (Hcfcore.getInstance().KITMAP_MODE) {
            player.sendMessage(ChatColor.GREEN + "Get kits from spawn!");
        } else {
            player.sendMessage(ChatColor.RED + "In the absence of kits, one must forge their own path, unbound by preordained tools, embracing the journey of self-discovery and the art of creation");
        }

        return true;
    }
}
//
//        if (args.length == 0) {
//            Inventory gui = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Kits Menu");
//            ItemStack diamond_glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getData());
//            ItemStack yellow_glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData());
//            ItemStack purple_glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.PURPLE.getData());
//
//            ItemMeta diamond_glass_meta = diamond_glass.getItemMeta();
//            ItemMeta yellow_glass_meta = yellow_glass.getItemMeta();
//            ItemMeta brown_glass_meta = purple_glass.getItemMeta();
//
//            diamond_glass_meta.setDisplayName(ChatColor.BLUE + "Diamond Kit");
//            yellow_glass_meta.setDisplayName(ChatColor.YELLOW + "Bard Kit");
//            brown_glass_meta.setDisplayName(ChatColor.DARK_PURPLE + "Archer Kit");
//
//            diamond_glass.setItemMeta(diamond_glass_meta);
//            yellow_glass.setItemMeta(yellow_glass_meta);
//            purple_glass.setItemMeta(brown_glass_meta);
//
//            gui.setItem(0, diamond_glass);
//            gui.setItem(1, yellow_glass);
//            gui.setItem(2, purple_glass);
//
//            player.openInventory(gui);
//
//
//            return true;
//        }
//
//        switch (args[0]) {
//            case "diamond":
//                if (player.hasPermission("hcf.kit.diamond") || Hcfcore.getInstance().KITMAP_MODE) {
//                    KitManager.diamondkit.givePlayerKit(player);
//                    break;
//                }
//
//                player.sendMessage(ChatColor.RED + "You don't have permission!");
//                break;
//            case "miner":
//                if (player.hasPermission("hcf.kit.miner") || Hcfcore.getInstance().KITMAP_MODE) {
//                    KitManager.minerkit.givePlayerKit(player);
//                    break;
//                }
//
//                player.sendMessage(ChatColor.RED + "You don't have permission!");
//                break;
//
//            case "bard":
//                if (player.hasPermission("hcf.kit.bard") || Hcfcore.getInstance().KITMAP_MODE) {
//                    KitManager.bardkit.givePlayerKit(player);
//                    break;
//                }
//
//                player.sendMessage(ChatColor.RED + "You don't have permission!");
//                break;
//
//            case "archer":
//                if (player.hasPermission("hcf.kit.archer") || Hcfcore.getInstance().KITMAP_MODE) {
//                    KitManager.archerkit.givePlayerKit(player);
//                    break;
//                }
//
//                player.sendMessage(ChatColor.RED + "You don't have permission!");
//                break;
//        }
//
//
//
//        return true;
//    }


