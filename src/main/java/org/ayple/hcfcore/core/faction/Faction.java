package org.ayple.hcfcore.core.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

public class Faction {

    public static final DecimalFormat DTR_FORMAT = new DecimalFormat("0.00");

    private final UUID factionID;
    public UUID getFactionID() { return this.factionID; }


//    public final Team team;



    private String factionName;
    public String getFactionName() { return this.factionName; }
    public void setFactionName(String new_name) { this.factionName = new_name; }

    private Location factionHQ;
    public void setFactionHQ(Location new_hq) {
        this.factionHQ = new_hq;
    }

    public Location getFactionHQ() { return this.factionHQ; }
    public String getFactionHQAsString() {
        if (this.factionHQ == null) {
            return "Not Set";
        }

        String x = Double.toString(this.factionHQ.getX());
        String y = Double.toString(this.factionHQ.getY());
        String z = Double.toString(this.factionHQ.getZ());
        return "X: " + x + " Y: " + y + " Z: " + z;
    }

    private final Claim claim;
    public Claim getClaim() { return this.claim; }

    private float dtr;
    public float getFactionDTR() { return this.dtr; }
    public void setFactionDTR(float new_dtr) {
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "UPDATE factions SET dtr = ?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setFloat(1, new_dtr);
                statement.executeUpdate();
                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        this.dtr = new_dtr;
    }

    public float getMaxDTR() {
        switch (getFactionMembersSize()) {
            case 2: return 2.01f;
            case 3:
            case 4:
                return 3.01f;
            default: return 1.01f;
        }
    }

    private double bal;
    public Double getFactionBal() { return this.bal; }
    public void setFactionBal(double new_bal) { this.bal = new_bal; }

    private final Hashtable<UUID, Integer> factionMembers = new Hashtable<UUID, Integer>();
    public Hashtable<UUID, Integer> getFactionMembers() { return this.factionMembers; }

    public boolean removeFactionMember(UUID player_id) {
        if (factionMembers.get(player_id) == 3) {
            System.out.println("Can't kick them, they are the leader!");
            return false;
        }

        factionMembers.remove(player_id);

        //        this.team.removeEntry(Bukkit.getOfflinePlayer(player_id).getName());
        return true;
    }

    public Collection<Player> getOnlineFactionMembets() {
        Collection<Player> onlinemembers = new ArrayList<Player>();
        for (UUID memberID : getFactionMembers().keySet()) {
            OfflinePlayer member = Bukkit.getOfflinePlayer(memberID);
            if (member.isOnline())
                onlinemembers.add(member.getPlayer());
        }

        return onlinemembers;
    }

    public void addFactionMember(UUID player_id, int rank) {
        if (factionMembers.get(player_id) != null) {
            System.out.println("POSSIBLE ERROR: adding a member to a faction they're already stored in?");
        }

        factionMembers.put(player_id, rank);
//        this.team.addEntry(Bukkit.getOfflinePlayer(player_id).getName());
    }

    public void changeFactionMemberRank(UUID player_id, int rank) {
        if (factionMembers.get(player_id) == null) {
            System.out.println("Failed to get player when changing player rank?? look into this.");
        }

        factionMembers.put(player_id, rank);
    }


    public void setFactionMembersFromDatabase() {
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "SELECT player_uuid, `rank` FROM faction_members WHERE faction_id=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, this.getFactionID().toString());
                statement.executeQuery();

                ResultSet results = statement.getResultSet();

                System.out.println(statement.toString());

                while (results.next()) {
                    if (factionMembers.get(UUID.fromString(results.getString("player_uuid"))) == null) {
                        factionMembers.put(
                                UUID.fromString(results.getString("player_uuid")),
                                results.getInt("rank")
                        );
                    }

                }

                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }


    // the player uuids that are invited
    private final HashSet<UUID> factionInvites = new HashSet<UUID>();
    public HashSet<UUID> getFactionInvites() { return this.factionInvites; }
    public void addFactionInvite(UUID player_uuid) { factionInvites.add(player_uuid); }
    public void removeFactionInvite(UUID player_uuid) { factionInvites.remove(player_uuid); }



    public Faction(UUID faction_id, String faction_name, float dtr, int bal, Location faction_hq, Claim claim)  {
        this.factionID = faction_id;
        this.factionName = faction_name;
        this.factionHQ = faction_hq;
        this.claim = claim;
        this.dtr = dtr;
        this.bal = bal;
    }

    public int getFactionMembersSize() {
        return this.getFactionMembers().size();
    }

    public boolean onDtrFreeze() {
        return this.getFactionDTR() != this.getMaxDTR();
    }

    public boolean factionFull() {
        return this.getFactionMembersSize() >= Hcfcore.getInstance().getTeamLimitSize();
    }

    public int getOnlineMembersSize() {
        int size = 0;

        for (UUID target_id : this.getFactionMembers().keySet()) {
            OfflinePlayer target_player = Bukkit.getOfflinePlayer(target_id);
            if (target_player.isOnline()) {
                size = size + 1;
            }
        }

        return size;
    }

    public void broadcastMessageToFaction(String message) {
        for (UUID target_id : this.getFactionMembers().keySet()) {
            OfflinePlayer target_player = Bukkit.getOfflinePlayer(target_id);
            if (target_player.isOnline()) {
                target_player.getPlayer().sendMessage(message);
            }
        }
    }

    public boolean factionContainsPlayer(UUID player_id) {
        return getFactionMembers().containsKey(player_id);
    }



//    public int getOnlineSize() {
//        return getOnlinePlayers().size();
//    }
//
//    public Set<Player> getOnlinePlayers() {
//        return factionMembers.stream()
//                .filter(OfflinePlayer::isOnline)
//                .map(p -> Bukkit.getPlayer(p.getUniqueId()))
//                .collect(Collectors.toSet());
//    }

}
