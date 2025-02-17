package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class OnEnderPearlEvent implements Listener {
    @EventHandler(ignoreCancelled = false)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getType() == EntityType.ENDER_PEARL) {
            if (event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Player) {
                Player player = (Player) event.getEntity().getShooter();

                if (!CooldownManager.hasEnderpearlCooldown(player.getUniqueId())) {
                    CooldownManager.registerEnderpearlCooldown(player);
                    return;
                }


                String seconds_left = Integer.toString(CooldownManager.getSecondsLeftOfEnderpearlCooldown(player.getUniqueId()));
                player.sendMessage(ChatColor.RED + "You are on enderpearl cooldown with " + seconds_left + "!");
                player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
                event.setCancelled(true);
            }
        }
    }
}
