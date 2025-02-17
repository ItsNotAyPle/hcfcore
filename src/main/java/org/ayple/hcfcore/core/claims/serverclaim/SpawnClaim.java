package org.ayple.hcfcore.core.claims.serverclaim;

import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class SpawnClaim {
    public static final String SPAWN = "Spawn";
    public static final UUID SPAWN_UUID = UUID.nameUUIDFromBytes(SPAWN.getBytes());
    private final Claim claim;

    public SpawnClaim() {
        World world = Bukkit.getWorld("world");
        this.claim = new Claim(
                SPAWN_UUID,
                "Spawn",
                new Location(
                        world,
                        ConfigHelper.getConfig().getDouble("server_claims.spawn.spawn_corner_1_x"),
                        0,
                        ConfigHelper.getConfig().getDouble("server_claims.spawn.spawn_corner_1_z")

                ),
                new Location(
                        world,
                        ConfigHelper.getConfig().getDouble("server_claims.spawn.spawn_corner_2_x"),
                        world.getMaxHeight(),
                        ConfigHelper.getConfig().getDouble("server_claims.spawn.spawn_corner_2_z")
                )
        );

        this.claim.setIsServerClaim(true);

        ClaimsManager.registerClaim(claim);

    }
}
