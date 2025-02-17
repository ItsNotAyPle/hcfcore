package org.ayple.hcfcore.ranks;

import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class RankHandler {
    // map player_ids to ranks ONLY WHEN THEY ARE ONLINE
//    public HashMap<UUID, AbstractRank>

    private static OwnerRank ownerRank = new OwnerRank();
//    private static MemberRank memberRank = new MemberRank();

    public static boolean insertNewMember(Player player) {
        try {
            String sql = "INSERT INTO player_ranks (player_id) VALUES (?)";
            HcfSqlConnection conn = new HcfSqlConnection();
            PreparedStatement statement = conn.getConnection().prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            statement.executeUpdate();
            conn.closeConnection();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void loadPlayerRank(Player player) {
        try {
            String sql = "SELECT * FROM player_ranks WHERE player_id=?";
            HcfSqlConnection conn = new HcfSqlConnection();
            PreparedStatement statement = conn.getConnection().prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            ResultSet results = statement.executeQuery();

            if (!results.next()) {
                System.out.println("player rank not fount????");
//                return memberRank;
            }

            int rank = results.getInt("player_rank");
            conn.closeConnection();

            switch (rank) {
//                case 1: return ownerRank;
            }


        } catch (SQLException e) {
            e.printStackTrace();
//            return memberRank;
        }
    }
}
