package org.ayple.hcfcore.core.claims;

import org.ayple.hcfcore.core.BalanceHandler;
import org.ayple.hcfcore.core.Cuboid;
import org.ayple.hcfcore.core.claims.serverclaim.SpawnClaim;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;

public class ClaimsManager {

    public static final String WILDERNESS = "wilderness";
    public static final String WARZONE = "warzone";

    // faction UUID
    // if faction UUID is
    private static Hashtable<UUID, Claim> claims = new Hashtable<UUID, Claim>();

    // This is used to know what claims players go in and out of. This is done to
    // prevent spam of `playerInClaim`
    // first UUID is player id, second is claim id
    private static HashMap<UUID, UUID> player_location_claims = new HashMap<UUID, UUID>();


    public static void removeClaim(UUID faction_id) {
        if (claims.containsKey(faction_id))
            claims.remove(faction_id);
    }

    public static void registerClaim(Claim claim) {
        claims.put(claim.getOwnerFactionID(), claim);
    }

    // this should be called if server is reset
    // gets all the stored claims data and turns it into claim objects
    public static void reloadClaims() {
        claims.clear();
        for (Faction faction : NewFactionManager.getFactions()) {
            Claim fac_claim = faction.getClaim();
            if (fac_claim == null) continue;

            claims.put(faction.getFactionID(), fac_claim);
        }

        // this code is only tested without the bukkit scheduler

//        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
//            try {
//                String sql = "SELECT id, faction_name, corner_1_x, corner_1_z, corner_2_x, corner_2_z FROM factions";
//                HcfSqlConnection conn = new HcfSqlConnection();
//                Statement statement = conn.getConnection().createStatement();
//                statement.executeQuery(sql);
//
//                ResultSet results = statement.getResultSet();
//                while (results.next()) {
//                    UUID owner_uuid = UUID.fromString(results.getString("id"));
//                    Claim new_claim = new Claim(
//                            owner_uuid,
//                            results.getString("faction_name"),
//                            results.getInt("corner_1_x"),
//                            results.getInt("corner_1_z"),
//                            results.getInt("corner_2_x"),
//                            results.getInt("corner_2_z")
//                    );
//
//                    claims.add(new_claim);
//                }
//
//                conn.closeConnection();
//                System.out.println(claims);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });

    }

    public static ArrayList<Cuboid> getAllClaimCuboids() {
        ArrayList<Cuboid> claims_cuboids = new ArrayList<Cuboid>();
        for (Claim claim : claims.values()) {
            claims_cuboids.add(claim.getCuboid());
        }

        return claims_cuboids;

    }


    // unused function, this already happens lmao - AyPle
    public static void saveClaimsToDisk() {

    }

    public static void newClaim(Player player, Selection selection, int price) throws SQLException {
        String sql = "UPDATE factions SET corner_1_x=?, corner_1_z=?, corner_2_x=?, corner_2_z=? WHERE id=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);

        Faction faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
        if (faction == null) {
            System.out.println("Failed to get faction from player id in newClaim!");
            conn.closeConnection();
            return;
        }

        UUID faction_id = faction.getFactionID();
        if (faction_id == null) {
            player.sendMessage("SQLERROR IN `ClaimsManager.newClaim`");
            conn.closeConnection();
            return;
        }

        statement.setInt(1, (int) Math.round(selection.getPos1().getX()));
        statement.setInt(2, (int) Math.round(selection.getPos1().getZ()));
        statement.setInt(3, (int) Math.round(selection.getPos2().getX()));
        statement.setInt(4, (int) Math.round(selection.getPos2().getZ()));
        statement.setString(5, faction_id.toString());

        statement.executeUpdate();


        conn.closeConnection();
        BalanceHandler.takeMoneyFromFaction(faction_id, price);
        claims.put(faction_id, new Claim(faction_id, faction.getFactionName(), selection.getPos1(), selection.getPos2()));
    }

    // TODO: optimize this jesus fucking christ almighty 10/07/23
    //TODO: a year later (13/08/24) i forgot what this does lmfao
    // 15/08/24 and the way i implemented checking what claim the players in
    // is to keep checking every time they move where they are and match it
    // to every claim which is despicable.
    public static void relayPlayerInClaim(Player player) {
        UUID player_id = player.getUniqueId();

        // if the player hasn't been registered just put in the player and set it to null
        if (!player_location_claims.containsKey(player_id)) {
            // https://stackoverflow.com/questions/29059530/is-there-any-way-to-generate-the-same-uuid-from-a-string
            player_location_claims.put(player_id, UUID.nameUUIDFromBytes(WILDERNESS.getBytes()));
        }

        UUID stored_claim_id = player_location_claims.get(player_id);
        boolean stored_in_wilderness = Objects.equals(stored_claim_id, UUID.nameUUIDFromBytes(WILDERNESS.getBytes()));
        boolean stored_in_warzone = Objects.equals(stored_claim_id, UUID.nameUUIDFromBytes(WARZONE.getBytes()));


        // loops through every claim and checks if teh players in it and then
        // stores the value, if no claim is fount then it's set to either wilderness
        // or warzone
        for (Claim claim : claims.values()) {
            if (claim.getCuboid() == null) continue;
            if (claim.getCuboid().contains(player.getLocation())) {
                UUID claim_id = claim.getOwnerFactionID();

                // players moved into new claim
                if (claim_id != stored_claim_id) {
                    if (claim_id == SpawnClaim.SPAWN_UUID) {
                        player.sendMessage(ChatColor.GREEN + "Now entering Spawn!");
                    } else {
                        player.sendMessage(ChatColor.GREEN + "Now entering " + ChatColor.RED + claim.getFactionName() + ChatColor.GREEN + "'s claim!");
                    }
                }

                player_location_claims.put(player_id, claim_id);
                return;
            }




            if (playerInWarzone(player)) {
                player_location_claims.put(player_id, UUID.nameUUIDFromBytes(WARZONE.getBytes()));
            } else {
                player_location_claims.put(player_id, UUID.nameUUIDFromBytes(WILDERNESS.getBytes()));
            }


//            player_location_claims.put(player_id, UUID.nameUUIDFromBytes(WILDERNESS.getBytes()));

        }


//        System.out.println("Warzone: " + stored_in_warzone);
//        System.out.println("Wildness: " + stored_in_wilderness);
//        System.out.println("Currently in: " + player_location_claims.get(player_id).toString());

        //if last place player was in was warzone and now in wilderness
        if (!stored_in_wilderness && Objects.equals(player_location_claims.get(player_id), UUID.nameUUIDFromBytes(WILDERNESS.getBytes()))) {
            player.sendMessage(ChatColor.GREEN + "Now entering Wilderness!");
            return;
        }

        if (!stored_in_warzone && Objects.equals(player_location_claims.get(player_id), UUID.nameUUIDFromBytes(WARZONE.getBytes()))) {
            player.sendMessage(ChatColor.RED + "Now entering Warzone!");
        }

//        if (!stored_in_wilderness || !stored_in_warzone) {
//            if (playerInWarzone(player)) {
//            } else {
//                player.sendMessage(ChatColor.GREEN + "Now entering Wilderness!");
//            }
//        }


    }

    public static boolean playerInWarzone(Player player) {
        return player.getLocation().getX() <= 250 && player.getLocation().getX() >= -250 && player.getLocation().getZ() >= -250 && player.getLocation().getZ() <= 250;
    }



    public static Claim getClaimPlayerIn(Player player) {

        for (Claim claim : claims.values()) {
            if (claim.getCuboid().contains(player.getLocation())) {
                return claim;
            }
        }

        return null;
    }

    public static Faction getFactionOwnerOfClaimPlayersIn(Player player) {

        for (Claim claim : claims.values()) {
            if (claim.getCuboid().contains(player.getLocation())) {
                return NewFactionManager.getFaction(claim.getOwnerFactionID());
            }
        }

        return null;
    }


    public static boolean playerOwnsClaimTheyreIn(Player player) {

        Faction faction = getFactionOwnerOfClaimPlayersIn(player);
        if (faction == null) {
            return false;
        }

//        for (Player member : faction.getFactionMembers()) {
//            if (member.getUniqueId().equals(player.getUniqueId())) {
//                return true;
//            }
//        }

        return faction.getFactionMembers().get(player.getUniqueId()) != null;
    }




    public static boolean blockInClaim(Location pos) {
        for (Cuboid claim : getAllClaimCuboids()) {
            if (claim.contains(pos)) return true;
        }

        return false;
    }

    public static Cuboid cuboid3Dto2D(Cuboid cuboid) {
        Location corner_1 = new Location(cuboid.getWorld(), cuboid.getLowerNE().getX(), 0, cuboid.getLowerNE().getZ());
        Location corner_2 = new Location(cuboid.getWorld(), cuboid.getUpperSW().getX(), 0, cuboid.getUpperSW().getZ());
        return new Cuboid(corner_1, corner_2);
    }


    // TODO: potentially find a better solution, should work
    // for a small server. Could cause problems as server
    // gets larger!
    public static boolean otherClaimInCuboid(Cuboid cuboid) {
        Cuboid flat_surface = cuboid3Dto2D(cuboid);
        for (Cuboid claim : getAllClaimCuboids()) {
            for (Block block : flat_surface.getBlocks()) {
                if (claim.contains(block.getLocation())) return true;
            }
        }

        return false;
    }


    public static boolean isClaimSizeLegal(Cuboid claim) {
        Cuboid flat_surface = cuboid3Dto2D(claim);
        return flat_surface.getVolume() >= 25 && flat_surface.getVolume() <= 3600;
    }

    public static int getClaimSizePrice(Cuboid claim) {
        Cuboid flat_surface = cuboid3Dto2D(claim);
        return flat_surface.getVolume() * 5;
    }


}
