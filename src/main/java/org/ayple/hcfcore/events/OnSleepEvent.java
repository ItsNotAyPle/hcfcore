package org.ayple.hcfcore.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class OnSleepEvent  implements Listener {
    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        event.setCancelled(true);
        event.getPlayer().sendMessage(ChatColor.RED + "You can't sleep in beds!");
    }
}
