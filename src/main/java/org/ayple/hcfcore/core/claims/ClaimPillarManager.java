package org.ayple.hcfcore.core.claims;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ClaimPillarManager {
    private static final HashMap<UUID, ClaimPillar> corner_1s = new HashMap<UUID, ClaimPillar>();
    private static final HashMap<UUID, ClaimPillar> corner_2s = new HashMap<UUID, ClaimPillar>();

    public static ClaimPillar getFirstCornerPillar(UUID player_id) { return corner_1s.get(player_id); }
    public static ClaimPillar getSecondCornerPillar(UUID player_id) { return corner_2s.get(player_id); }

    public static Location getFirstCornerAsLocation(UUID player_id) {
        ClaimPillar pillar = getFirstCornerPillar(player_id);
        if (pillar == null) {
            return null;
        }

        return new Location(pillar.world, pillar.getX(), 0, pillar.getZ());
    }

    public static Location getSecondCornerAsLocation(UUID player_id) {
        ClaimPillar pillar = getSecondCornerPillar(player_id);
        if (pillar == null) {
            return null;
        }

        return new Location(pillar.world, pillar.getX(), pillar.world.getMaxHeight(), pillar.getZ());
    }

    public static void addCorner1(Player player, int x, int z) {
        if (getFirstCornerPillar(player.getUniqueId()) != null) {
            if (!removeCorner1Pillar(player)) {
                System.out.println("FAILED TO REMOVE FIRST PILLAR FOR " + player.getDisplayName() + "!");
            }
        }

        ClaimPillar pillar = new ClaimPillar(player.getWorld(), player, x, z);
        corner_1s.put(player.getUniqueId(), pillar);
    }

    public static void addCorner2(Player player, int x, int z) {
        if (getSecondCornerPillar(player.getUniqueId()) != null) {
            if (!removeCorner2Pillar(player)) {
                System.out.println("FAILED TO REMOVE SECOND PILLAR FOR [" + player.getDisplayName() + "] !");
            }
        }

        ClaimPillar pillar = new ClaimPillar(player.getWorld(), player, x, z);
        corner_2s.put(player.getUniqueId(), pillar);
    }

    public static void addCorner2(Player player, double x, double z) {
        if (getSecondCornerPillar(player.getUniqueId()) != null) {
            if (!removeCorner2Pillar(player)) {
                System.out.println("FAILED TO REMOVE SECOND PILLAR FOR [" + player.getDisplayName() + "] !");
            }
        }

        ClaimPillar pillar = new ClaimPillar(player.getWorld(), player, x, z);
        corner_2s.put(player.getUniqueId(), pillar);
    }

    public static boolean removeCorner1Pillar(Player player) {
        ClaimPillar pillar = corner_1s.get(player.getUniqueId());
        if (pillar == null) {
            return false;
        }

        pillar.destroyPillar();
        corner_1s.remove(player.getUniqueId());
        return true;
    }

    public static boolean removeCorner2Pillar(Player player) {
        ClaimPillar pillar = corner_2s.get(player.getUniqueId());
        if (pillar == null) {
            return false;
        }

        pillar.destroyPillar();
        corner_2s.remove(player.getUniqueId());
        return true;
    }






}
