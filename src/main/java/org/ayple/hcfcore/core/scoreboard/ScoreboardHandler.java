package org.ayple.hcfcore.core.scoreboard;

import com.google.common.collect.Iterables;
import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.faction.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class ScoreboardHandler {
    private static final long UPDATE_TICK_INTERVAL = 2L;

    private final Map<UUID, PlayerBoard> playerBoards = new HashMap<>();
    private final TimerSidebarProvider timerSidebarProvider;

    public Map<UUID, PlayerBoard> getPlayerBoards() {
        return this.playerBoards;
    }

    public ScoreboardHandler() {

        timerSidebarProvider = new TimerSidebarProvider();

        // Give all online players a scoreboard.
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            applyBoard(player).addUpdates(players);
        }
    }

    public void onPlayerJoinServer(Player player) {

        // Update this player for every other online player.
        for (PlayerBoard board : playerBoards.values()) {
            board.addUpdate(player);
        }

        PlayerBoard board = applyBoard(player);
        board.addUpdates(Bukkit.getOnlinePlayers());
        board.setSidebarVisible(true);
    }

    public void onPlayerQuitServer(Player player) {
        PlayerBoard board = playerBoards.get(player.getUniqueId());
        if (board == null) return;
        board.runnable.cancel();
        playerBoards.remove(player.getUniqueId()).remove();
    }

    public void onPlayerJoinedFaction(Player player, Faction target_faction) {
        Collection<Player> players = target_faction.getOnlineFactionMembets();
        getPlayerBoard(player.getUniqueId()).addUpdates(players);
        for (Player target : players) {
            getPlayerBoard(target.getUniqueId()).addUpdate(player);
        }
    }

    public void onPlayerLeftFaction(Player player, Faction target_faction) {
        Collection<Player> players = target_faction.getOnlineFactionMembets();
        getPlayerBoard(player.getUniqueId()).addUpdates(players);
        for (Player target : players) {
            getPlayerBoard(target.getUniqueId()).addUpdate(player);
        }
    }

//    public void onFactionAllyCreate(FactionRelationCreateEvent event) {
//        Iterable<Player> updates = Iterables.concat(
//                event.getSenderFaction().getOnlinePlayers(),
//                event.getTargetFaction().getOnlinePlayers()
//        );
//
//        for (PlayerBoard board : playerBoards.values()) {
//            board.addUpdates(updates);
//        }
//    }

//    public void onFactionAllyRemove(FactionRelationRemoveEvent event) {
//        Iterable<Player> updates = Iterables.concat(
//                event.getSenderFaction().getOnlinePlayers(),
//                event.getTargetFaction().getOnlinePlayers()
//        );
//
//        for (PlayerBoard board : playerBoards.values()) {
//            board.addUpdates(updates);
//        }
//    }

    public PlayerBoard getPlayerBoard(UUID uuid) {
        return playerBoards.get(uuid);
    }

    public PlayerBoard applyBoard(Player player) {
        PlayerBoard board = new PlayerBoard(player);
        PlayerBoard previous = playerBoards.put(player.getUniqueId(), board);
        if (previous != null) {
            previous.remove();
        }

        board.setSidebarVisible(true);
        board.setDefaultSidebar(timerSidebarProvider, UPDATE_TICK_INTERVAL);
        return board;
    }

    public void clearBoards() {
        Iterator<PlayerBoard> iterator = playerBoards.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().remove();
            iterator.remove();
        }
    }
}
