package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.BalanceHandler;
import org.ayple.hcfcore.playerdata.PlayerData;
import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class BuyLifeSignEvent implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("hcf.create.shop_sign")) return;

        if (e.getLine(0).equalsIgnoreCase("[Buy Life]")) {
            if (e.getLine(1).isEmpty()) {
                player.sendMessage(ChatColor.RED + "Shop price is empty!");
                e.setCancelled(true);
                return;
            }


            e.setLine(0, ChatColor.GREEN + e.getLine(0));
            e.setLine(1, ChatColor.GREEN + "$" + e.getLine(1));
        }

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getClickedBlock() == null) return;

        if (e.getClickedBlock().getState() instanceof Sign) {
            Sign sign = (Sign) e.getClickedBlock().getState();

            if (sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[Buy Life]")) {
                int price = Integer.parseInt(ChatColor.stripColor(sign.getLine(1).replace("$", "")));

                if (BalanceHandler.getPlayerBalance(player) >= price) {
                    BalanceHandler.takeMoneyFromPlayer(player, price);
                    PlayerDataHandler.incrementLivesAmountForPlayer(player);
                    player.sendMessage(ChatColor.GREEN + "Bought 1 life for " + sign.getLine(1) + "!");
                } else {
                    player.sendMessage(ChatColor.RED + "Insufficient funds!");
                }
            }
        }
    }
}