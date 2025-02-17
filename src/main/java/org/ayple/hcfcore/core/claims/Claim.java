package org.ayple.hcfcore.core.claims;

import org.ayple.hcfcore.core.Cuboid;
import org.ayple.hcfcore.core.claims.serverclaim.SpawnClaim;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class Claim {
    private final UUID owner_faction_id;
    public UUID getOwnerFactionID() { return  this.owner_faction_id; }

    private final String faction_name;
    public String getFactionName() { return this.faction_name; }

    private boolean is_server_claim = false;
    public void setIsServerClaim(boolean value) {
        this.is_server_claim = value;
    }

    public boolean isServerClaim() {
        return this.is_server_claim;
    }

    private final Location corner_1;
    private final Location corner_2;
    private final Cuboid bounding_box;

    public Cuboid getCuboid() {
        return this.bounding_box;
    }

    public Claim(World world, String faction_name, int corner_1_x, int corner_1_z, int corner_2_x, int corner_2_z) {
        this.corner_1 = new Location(world, corner_1_x, 0, corner_1_z);
        this.corner_2 = new Location(world, corner_2_x, world.getMaxHeight(), corner_2_z);
        this.owner_faction_id = null;
        this.faction_name = faction_name;

        this.bounding_box = new Cuboid(this.corner_1, this.corner_2);
    }

    public Claim(World world, String faction_name, Location corner_1, Location corner_2) {
        this.corner_1 = corner_1;
        this.corner_2 = corner_2;
        this.owner_faction_id = null;
        this.faction_name = faction_name;

        this.bounding_box = new Cuboid(this.corner_1, this.corner_2);
    }


    public Claim(UUID owner_faction_id, String faction_name, int corner_1_x, int corner_1_z, int corner_2_x, int corner_2_z) {
        World world = Bukkit.getWorld("world");
        this.corner_1 = new Location(world, corner_1_x, 0, corner_1_z);
        this.corner_2 = new Location(world, corner_2_x, world.getMaxHeight(), corner_2_z);
        this.owner_faction_id = owner_faction_id;
        this.faction_name = faction_name;

        this.bounding_box = new Cuboid(this.corner_1, this.corner_2);
    }

    public Claim(UUID owner_faction_id, String faction_name, Location corner_1, Location corner_2) {
        this.corner_1 = corner_1;
        this.corner_2 = corner_2;
        this.owner_faction_id = owner_faction_id;
        this.faction_name = faction_name;

        this.bounding_box = new Cuboid(corner_1, corner_2);
    }

    public Faction getOwnerFaction() {
        if (!is_server_claim)
            return NewFactionManager.getFaction(getOwnerFactionID());
        return null;
    }

    public boolean isClaimSpawn() {
        return owner_faction_id.equals(SpawnClaim.SPAWN_UUID);
    }
}
