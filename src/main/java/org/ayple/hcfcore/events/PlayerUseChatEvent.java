package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.Objects;
import java.util.UUID;

public class PlayerUseChatEvent implements Listener {
//    private String OWNER_UUID = "e1de07bd-272b-4e63-adf7-66f8267468ae";
//    private String CRAZY_UUID = "e1de07bd-272b-4e63-adf7-66f8267468ae";
//    private String ALFIE_UUID = "e0c7b962-88f3-455f-b2e8-71b278eb2833";
//    private String GALAXY_UUID = "";

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        final Player p = event.getPlayer();
        final String msg = event.getMessage();
        event.setCancelled(true);
        Hcfcore.getInstance().broadcastPlayerMessage(p, msg);

        //        String rank = "";
///
//        if (Objects.equals(p.getUniqueId().toString(), OWNER_UUID)) {
//            rank = ChatColor.RED + "[Owner] ";
//        } else if (Objects.equals(p.getUniqueId().toString(), GALAXY_UUID)) {
//            rank = ChatColor.GOLD + "[Youtuber] ";
//        } else if (Objects.equals(p.getUniqueId().toString(), CRAZY_UUID)) {
//            rank = ChatColor.DARK_BLUE + "[Sgt.Mjr] ";
//        } else if (Objects.equals(p.getUniqueId().toString(), ALFIE_UUID)) {
//            rank = ChatColor.LIGHT_PURPLE + "[Builder] ";
//        }

//        Faction faction = NewFactionManager.getFactionFromPlayerID(p.getUniqueId());
//        if (faction == null) {
//            Bukkit.broadcastMessage(rank + ChatColor.YELLOW + p.getDisplayName() + ": " + ChatColor.RESET + msg);
//            event.setCancelled(true);
//            return;
//        }
//
//
//        Bukkit.broadcastMessage(rank + ChatColor.YELLOW + "[" + faction.getFactionName() + "] " + ChatColor.WHITE + p.getDisplayName() + ": " + msg);
//        event.setCancelled(true);
    }

}
