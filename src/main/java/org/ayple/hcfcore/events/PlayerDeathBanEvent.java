package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.ayple.hcfcore.helpers.PlayerHelpers;
import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class PlayerDeathBanEvent implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();

        if (player.hasMetadata("LOGOUT VILLAGER DEATH") && player.getMetadata("LOGOUT VILLAGER DEATH").get(0).asBoolean()) {
            player.removeMetadata("LOGOUT VILLAGER DEATH", Hcfcore.getInstance());
            return;
        }

        if (player.getKiller() != null) {
            PlayerDataHandler.increasePlayerKillCount(player.getKiller().getUniqueId());
        }

        Faction player_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
        if (player_faction != null) {
            NewFactionManager.decreaseDTR(player_faction.getFactionID());
        }

        if (Hcfcore.getInstance().KITMAP_MODE) return;

        PlayerHelpers.onPlayerDeath(player);

        if (player.isOnline()) {
            player.kickPlayer("You died, banned for 4 hours");
        }

    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        if (player.isBanned()) {
            String ban_reason = event.getKickMessage();
            if (ban_reason.toLowerCase().contains("death ban")) {
                if (PlayerDataHandler.getPlayerData(event.getPlayer().getUniqueId()).getLives() > 0) {
//                    Bukkit.getServer().getOfflinePlayer(player.getUniqueId()).setBanned(false);
                    Bukkit.getServer().getBanList(BanList.Type.NAME).pardon(player.getName());
                    event.setResult(PlayerLoginEvent.Result.ALLOWED);
                    event.setKickMessage(null);

                    PlayerDataHandler.decrementLivesAmountForPlayer(player);
                    player.sendMessage(ChatColor.GREEN + "Revived by using a life!");

                }
            }
        }
    }
}
