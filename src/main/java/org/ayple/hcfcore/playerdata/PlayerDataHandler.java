package org.ayple.hcfcore.playerdata;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataHandler {
    private final static HashMap<UUID, PlayerData> player_data = new HashMap<UUID, PlayerData>();
    public static ArrayList<PlayerData> getAllPlayerData() { return new ArrayList<PlayerData>(player_data.values()); }
    public static PlayerData getPlayerData(UUID player_id) { return player_data.get(player_id); }

    public static void loadAllPlayerData() {
        try {
            String sql = "SELECT * FROM player_data";
            HcfSqlConnection conn = new HcfSqlConnection();
            ResultSet rs = conn.getConnection().prepareStatement(sql).executeQuery();

            while (rs.next()) {
                UUID player_id = UUID.fromString(rs.getString("player_id"));
                int lives = rs.getInt("lives");
                int kills = rs.getInt("kills");
                player_data.put(player_id, new PlayerData(player_id, lives, kills));
            }


            conn.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean playerLoggedInBefore(UUID player_id)  {
//        boolean return_value;
//
//        String sql = "SELECT player_uuid FROM player_data WHERE player_uuid=?";
//        HcfSqlConnection conn = new HcfSqlConnection();
//        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
//        statement.setString(1, player_id.toString());
//        statement.executeQuery();
//
//        return_value = statement.getResultSet().next();
//        conn.closeConnection();
//
//        return return_value;

        System.out.println(player_data.get(player_id));
        return player_data.get(player_id) != null;
    }



    public static void onLoginFirstTime(UUID player_id) {
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "INSERT INTO player_data (player_id) VALUES (?)";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, player_id.toString());
                statement.executeUpdate();
                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        player_data.put(player_id, new PlayerData(player_id, 0, 0));
    }


    public static void increasePlayerKillCount(UUID killer) {
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "UPDATE player_data SET kills = kills + 1 WHERE player_id=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, killer.toString());
                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        getPlayerData(killer).incrementKills();
    }


    public static void setNewLivesForPlayer(OfflinePlayer player, int amount) {
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "UPDATE player_data SET lives = ? WHERE player_id=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setInt(1, amount);
                statement.setString(2, player.getUniqueId().toString());
                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        getPlayerData(player.getUniqueId()).setPlayerLives(amount);
    }

    public static void incrementLivesAmountForPlayer(OfflinePlayer player) {
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "UPDATE player_data SET lives = lives + 1 WHERE player_id=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, player.getUniqueId().toString());
                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        getPlayerData(player.getUniqueId()).incrementPlayerLives();
    }

    public static void decrementLivesAmountForPlayer(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "UPDATE player_data SET lives = lives - 1 WHERE player_id=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, player.getUniqueId().toString());
                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        getPlayerData(player.getUniqueId()).decrementPlayerLives();
    }


}
