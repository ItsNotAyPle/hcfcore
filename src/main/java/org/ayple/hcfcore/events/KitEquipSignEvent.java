package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.kits.kitmap.KitmapManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class KitEquipSignEvent implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("hcf.create.kitsign")) return;

        if (e.getLine(0).equalsIgnoreCase("[Kit]")) {
            e.setLine(1, ChatColor.BLUE + e.getLine(1));
        }
    }


    //TODO: add metadata checker to make sure the sign is made by us
    // instead of checking claim
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!Hcfcore.getInstance().KITMAP_MODE) return;
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!ClaimsManager.getClaimPlayerIn(player).isClaimSpawn()) return;
        if (event.getClickedBlock().getState() instanceof Sign) {

            Sign sign = (Sign) event.getClickedBlock().getState();
            if (sign.getLine(1).equals(ChatColor.BLUE + "Diamond")) {
                KitmapManager.diamondkit.givePlayerKit(player);
                player.getInventory().clear();
            }

            else if (sign.getLine(1).equals(ChatColor.BLUE + "Miner")) {
                KitmapManager.minerkit.givePlayerKit(player);
                player.getInventory().clear();

            }

            else if (sign.getLine(1).equals(ChatColor.BLUE + "Bard")) {
                KitmapManager.bardkit.givePlayerKit(player);
                player.getInventory().clear();

            }

            else if (sign.getLine(1).equals(ChatColor.BLUE + "Archer")) {
                KitmapManager.archerkit.givePlayerKit(player);
                player.getInventory().clear();

            }

        }
    }
}
