package org.ayple.hcfcore.core.claims;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class ClaimPillar {
    private final Player owner;
    private final double x;
    public double getX() { return this.x; }

    private final double z;
    public double getZ() { return this.z; }

    public final World world;


    public UUID getOwnerPlayerID() { return this.owner.getUniqueId(); }

    // Currently these can cause some issues.



    public HashSet<Location> affected_blocks = new HashSet<Location>();

    public ClaimPillar(World world, Player owner, double x, double z) {
        this.x = x;
        this.z = z;
        this.world = world;
        this.owner = owner;
        placePillar();
    }

    public void placePillar() {
        for (int y = 50; y < 160; y++) {
            Location pos = new Location(world, this.x, y, this.z);
            Block block = world.getBlockAt(pos);

            if (block.getType() == Material.AIR) {
                this.owner.sendBlockChange(pos, Material.GLASS, (byte) 0);
                this.affected_blocks.add(pos);
            }
        }
    }

    public void destroyPillar()  {
        for (Location pos : affected_blocks) {
            this.owner.sendBlockChange(pos, Material.AIR, (byte) 0);
        }
    }
}
