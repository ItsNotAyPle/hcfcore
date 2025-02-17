package org.ayple.hcfcore.helpers;

import org.ayple.hcfcore.Hcfcore;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

public class PlayerHelpers {

    public static Player GetPlayerInstanceFromCommand(CommandSender sender) {
        if (sender instanceof Player) {
            return (Player) sender;
        }

        return null;
    }


    public static boolean playerInFaction(Player player) {
        return true;
    }

    public static void onPlayerDeath(Player player) {
//        Date unban_date = new Date(System.currentTimeMillis() + (1));
        Date unban_date = new Date(System.currentTimeMillis() + (60 * 60 * 1000));
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), "Death banned!", unban_date, null);
    }

    public static void teleportPlayerToSpawn(Player player) {
        player.teleport(new Location(Bukkit.getServer().getWorlds().get(0), 0, 100, 0));
    }
}
