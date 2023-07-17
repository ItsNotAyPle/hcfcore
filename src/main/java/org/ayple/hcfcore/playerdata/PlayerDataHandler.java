package org.ayple.hcfcore.playerdata;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
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
                int bal = rs.getInt("balance");
                int kills = rs.getInt("kills");
                System.out.println(player_id);
                System.out.println(bal);
                System.out.println(kills);
                player_data.put(player_id, new PlayerData(player_id, bal, kills));
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

        player_data.put(player_id, new PlayerData(player_id, 500, 0));
    }


    public static void increasePlayerKillCount(Player killer) {
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "UPDATE player_data SET kills = kills + 1 WHERE player_uuid=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, killer.getUniqueId().toString());
                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        getPlayerData(killer.getUniqueId()).incrementKills();
    }


}