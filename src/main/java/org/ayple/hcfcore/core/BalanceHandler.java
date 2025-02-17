package org.ayple.hcfcore.core;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.ayple.hcfcore.playerdata.PlayerData;
import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class BalanceHandler {


    // these functions don't check the original amount, that has to be done before hand
    // TODO: make it check the original amount (maybe)
    public static void giveMoneyToPlayer(Player player, double amount) {
        Economy economy = Hcfcore.getEconomy();
        EconomyResponse response = economy.depositPlayer(player, amount);

        if (response.transactionSuccess()) {
            player.sendMessage(ChatColor.RED + "Deposited " + economy.format(response.amount) + " into your account!");
        } else {
            player.sendMessage(ChatColor.RED + "ERROR");
            System.out.println(response.errorMessage);
        }




        //        PlayerData data = PlayerDataHandler.getPlayerData(player.getUniqueId());






        //        data.setPlayerBalance(data.getBalance() + amount);


//        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(),  () -> {
//            try {
//                String sql = "UPDATE player_data SET balance = balance + ? WHERE player_id=?";
//                HcfSqlConnection conn = new HcfSqlConnection();
//                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
//                statement.setInt(1, amount);
//                statement.setString(2, player.getUniqueId().toString());
//                statement.executeUpdate();
//                System.out.println(statement);
//                conn.closeConnection();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });

    }

    public static void takeMoneyFromPlayer(Player player, double amount)  {
        Economy economy = Hcfcore.getEconomy();
        EconomyResponse response = economy.withdrawPlayer(player, amount);

        if (response.transactionSuccess()) {
            player.sendMessage(ChatColor.RED + "Removed " + economy.format(response.amount) + " from your account!");
        } else {
            player.sendMessage(ChatColor.RED + "ERROR");
            System.out.println(response.errorMessage);
        }


//        PlayerData data = PlayerDataHandler.getPlayerData(player.getUniqueId());

        //        data.setPlayerBalance(data.getBalance() - amount);

//        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(),  () -> {
//            try {
//                String sql = "UPDATE player_data SET balance= balance - ? WHERE player_id=?";
//                HcfSqlConnection conn = new HcfSqlConnection();
//                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
//                statement.setInt(1, amount);
//                statement.setString(2, player.getUniqueId().toString());
//                statement.executeUpdate();
//
//                System.out.println(statement);
//
//                conn.closeConnection();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });
    }

    public static void takeMoneyFromFaction(UUID faction_id, double amount) {


        Faction faction = NewFactionManager.getFaction(faction_id);
        if (faction == null ) {
            System.out.println("Somehow the faction is null [takeMoneyFromFaction]");
            return;
        }

        faction.setFactionBal(faction.getFactionBal() - amount);
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(),  () -> {
            try {
                String sql = "UPDATE factions SET faction_bal = faction_bal - ? WHERE id= ?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setDouble(1, amount);
                statement.setString(2, faction_id.toString());
                statement.executeUpdate();

                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void giveMoneyToFaction(UUID faction_id, double amount) {

        Faction target_faction = NewFactionManager.getFaction(faction_id);
        if (target_faction == null) {
            System.out.println("Somehow the faction is null [giveMoneyToFaction]");
            return;
        }

        target_faction.setFactionBal(target_faction.getFactionBal() + amount);

        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(),  () -> {
            try {
                String sql = "UPDATE factions SET faction_bal = faction_bal + ? WHERE id= ?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setDouble(1, amount);
                statement.setString(2, faction_id.toString());
                statement.executeUpdate();

                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public static double getPlayerBalance(Player player) {
//        return PlayerDataHandler.getPlayerData(player.getUniqueId()).getBalance();
        return Hcfcore.getEconomy().getBalance(player);
    }

//    public static int getFactionBalance(Faction faction) {
//        return faction.getFactionBal();
//    }
//
//    public static int getFactionBalance(Player player) {
//        return NewFactionManager.getFactionFromPlayerID(player.getUniqueId()).getFactionBal();
//    }



}
