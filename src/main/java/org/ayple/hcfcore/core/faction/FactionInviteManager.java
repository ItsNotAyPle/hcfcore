package org.ayple.hcfcore.core.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class FactionInviteManager {
    // faction id, set of player ids
//    public static final ArrayList<FactionInvite> invites = new ArrayList<FactionInvite>();
//
//    public static void registerNewInvite(FactionInvite invite) {
//        invites.add(invite);
//    }

    public static boolean registerNewInvite(UUID faction_id, UUID target_player_id) {
        Faction faction = NewFactionManager.getFaction(faction_id);
        if (faction == null) {
            System.out.println("Unable to find the faction? LOOK INTO THIS!");
            return false;
        }

        faction.addFactionInvite(target_player_id);
        System.out.println("Registered new invite! " + faction_id + "[!:!] " + target_player_id);
        return true;
    }


    public static void onPlayerJoinFaction(UUID faction_id, Player player) {
        Faction faction = NewFactionManager.getFaction(faction_id);
        if (faction == null) {
            System.out.println("Faction wasn't fount? aborting.");
            return;
        }

        faction.addFactionMember(player.getUniqueId(), 0);
        faction.removeFactionInvite(player.getUniqueId());
        Hcfcore.getInstance().getScoreboardHandler().onPlayerJoinedFaction(player, faction);

        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "INSERT INTO faction_members (faction_id, player_uuid) VALUES (?, ?)";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, faction_id.toString());
                statement.setString(2, player.getUniqueId().toString());
                statement.executeUpdate();
                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        faction.broadcastMessageToFaction(ChatColor.GREEN + player.getName() + "Has joined your faction!");

        // TODO: change this into a reset dtr method
        faction.setFactionDTR(faction.getMaxDTR());



//        if (!lookForInvite(invite)) {
//            System.out.println("Error looking for invite in 'onPlayerJoinFaction'");
//            return;
//        }

//        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
//            try {
//                String sql = "INSERT INTO FACTION_MEMBERS (faction_id, player_uuid) VALUES (?, ?)";
//                HcfSqlConnection conn = new HcfSqlConnection();
//                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
//                statement.setString(1, invite.faction_id.toString());
//                statement.setString(2, invite.player_id.toString());
//                statement.executeUpdate();
//                conn.closeConnection();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });
//
//        remove(invite);


    }

//    private static void remove(FactionInvite invite) {
//        for (int i = 0; i < invites.size(); i++) {
//            if (invite.faction_id == invites.get(i).player_id && invite.faction_id == invites.get(i).faction_id) {
//                invites.remove(i);
//            }
//        }
//    }
//
//    public static boolean lookForInvite(FactionInvite provided_invite) {
//        for (FactionInvite invite : invites) {
//            System.out.println(invite.player_id);
//            System.out.println(provided_invite.player_id);
//            System.out.println(invite.faction_id);
//            System.out.println(provided_invite.faction_id);
//            if (invite.player_id.equals(provided_invite.player_id) && invite.faction_id.equals(provided_invite.faction_id)) {
//                System.out.println("GOT HERE HSGUHGSI)JGIS)UG");
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public static boolean lookForInvite(UUID player_id, UUID faction_id) {
//        for (FactionInvite invite : invites) {
//            if (invite.faction_id == player_id && invite.faction_id == faction_id ) {
//                return true;
//            }
//        }
//
//        return false;
//    }
}
