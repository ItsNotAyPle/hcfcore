package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.scoreboard.PlayerBoard;
import org.ayple.hcfcore.core.scoreboard.ServerScoreboard;
import org.ayple.hcfcore.helpers.PlayerHelpers;
import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Date;

public class PlayerJoinedServerEvent implements Listener {


    // the logout villagers might have been the most frustrating thing i have
    // ever worked on. There was just issue after issue and this is the only
    // way i've fount to get it working in some way. TODO: refactor in future
    @EventHandler
    public void onPlayerAttemptJoin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (CooldownManager.UUIDInLogoutVillagerDiedSet(player.getUniqueId())) {
            CooldownManager.removeDeadLogoutVillager(player.getUniqueId());
            player.setMetadata("LOGOUT VILLAGER DEATH", new FixedMetadataValue(Hcfcore.getInstance(), true));
            PlayerHelpers.onPlayerDeath(player);
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "logout villager died");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasMetadata("LOGOUT VILLAGER DEATH") && player.getMetadata("LOGOUT VILLAGER DEATH").get(0).asBoolean()) {
            player.getInventory().clear();
            PlayerHelpers.teleportPlayerToSpawn(player);
            CooldownManager.registerPvpTimer(event.getPlayer());
        }

        if (!PlayerDataHandler.playerLoggedInBefore(player.getUniqueId())) {
            if (Hcfcore.getInstance().KITMAP_MODE) return;
            PlayerDataHandler.onLoginFirstTime(player.getUniqueId());
            giveStarterItems(player);
            CooldownManager.registerPvpTimer(player);
        }

        if (CooldownManager.hasLogoutVillager(player.getUniqueId())) {
            CooldownManager.cancelLogoutVillager(player.getUniqueId());
            // TODO: teleport player to logout villager instead of where they logged out
        }


//        if (CooldownManager.playerHasPvpTimer(player)) {
//            player.sendMessage(ChatColor.GREEN + "Detected pvp timer, adding it to scoreboard!");
//            CooldownManager.showPvpTimer(player);
//        }

        Hcfcore.getInstance().getScoreboardHandler().onPlayerJoinServer(player);
    }

    private void giveStarterItems(Player player) {
        ItemStack steak = new ItemStack(Material.COOKED_BEEF);
        ItemStack fishing_rod = new ItemStack(Material.FISHING_ROD);
        fishing_rod.addEnchantment(Enchantment.LURE, 2);
        steak.setAmount(32);
        player.getInventory().addItem(steak);
        player.getInventory().addItem(fishing_rod);
        player.sendMessage(ChatColor.GOLD + "Welcome to the server for the first time!");
    }


}
