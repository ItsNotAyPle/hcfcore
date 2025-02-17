package org.ayple.hcfcore.core.claims.serverclaim;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.Cuboid;
import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.core.claims.Selection;
//import org.ayple.hcfcore.core.claims.ServerClaim;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;


// Claim will be dealth with world guard instead!
// along with all server side claims

//public class GlowstoneMountainClaim extends ServerClaim {
    public class GlowstoneMountainClaim {
//    private Claim glowstoneClaim;
//    private Selection glowstoneRespawnArea;

    private static GlowstoneMountainClaim instance;

    public GlowstoneMountainClaim() {
        // singletons woo
        if (instance != this && instance != null) {
            return;
        }

        instance = this;
//        int claim_corner_1_x = ConfigHelper.getConfig().getInt("server_claims.glowstone_mountain.claim_corner_1_x");
//        int claim_corner_1_z = ConfigHelper.getConfig().getInt("server_claims.glowstone_mountain.claim_corner_1_z");
//        int claim_corner_2_x = ConfigHelper.getConfig().getInt("server_claims.glowstone_mountain.claim_corner_2_x");
//        int claim_corner_2_z = ConfigHelper.getConfig().getInt("server_claims.glowstone_mountain.claim_corner_2_z");
        double respawn_corner_1_x = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_1_x");
        double respawn_corner_1_y = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_1_y");
        double respawn_corner_1_z = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_1_z");
        double respawn_corner_2_x = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_2_x");
        double respawn_corner_2_y = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_2_y");
        double respawn_corner_2_z = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_2_z");


//        this.glowstoneClaim = new Claim(Bukkit.getWorld("nether"), "Glowstone Mountain", claim_corner_1_x, claim_corner_1_z, claim_corner_2_x, claim_corner_2_z);
        //this.glowstoneRespawnArea = new Selection(new Location(respawn_corner_1_x, respawn_corner_1_y, respawn_corner_1_z), new Location(respawn_corner_2_x, respawn_corner_2_y, respawn_corner_2_z));

        Location corner_1 = new Location(Bukkit.getWorld("world_nether"), respawn_corner_1_x, respawn_corner_1_y, respawn_corner_1_z);
        Location corner_2 = new Location(Bukkit.getWorld("world_nether"), respawn_corner_2_x, respawn_corner_2_y, respawn_corner_2_z);
        Cuboid regen_area = new Cuboid(corner_1, corner_2);

        new BukkitRunnable() {
            @Override
            public void run() {
                Hcfcore.getInstance().broadcastMessage(ChatColor.GOLD + "Glowstone mountain has been reset!");
                fillGlowstoneArea(regen_area);
            }
        }.runTaskTimer(Hcfcore.getInstance(), 0, 36_000);

    }

    private void fillGlowstoneArea(Cuboid regen_area) {
        for (Block block: regen_area.getBlocks()) {
            block.setType(Material.GLOWSTONE);
        }
    }
}
