package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class GoldenAppleListenerEvent implements Listener {
    @EventHandler(ignoreCancelled = false)
    public void onPlayeritemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() == null || event.getItem().getType() != Material.GOLDEN_APPLE) {
            return;
        }

        if (event.getItem().isSimilar(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1))) {
            player.sendMessage(ChatColor.RED + "Enchanted golden apples are disabled!");
            event.setCancelled(true);
            return;
        }

        if (CooldownManager.hasCrappleCooldown(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You are currently on golden apple cooldown!");
            event.setCancelled(true);
            return;
        }

        CooldownManager.registerCrappleCooldown(event.getPlayer());
    }


}
