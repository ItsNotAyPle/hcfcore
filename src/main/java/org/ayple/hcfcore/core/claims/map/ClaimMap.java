package org.ayple.hcfcore.core.claims.map;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ClaimMap {
    public final double x1,  z1,  x2,  z2, x3, z3,  x4,  z4;
    public final Location corner1, corner2, corner3, corner4;
    private final World world;

    private ArrayList<Location> affected_blocks = new ArrayList<Location>();



    public ClaimMap(World world, double x1, double z1, double x2, double z2,
                    double x3, double z3, double x4, double z4)
    {
        this.world = world;
        this.x1 = x1;
        this.z1 = z1;
        this.x2 = x2;
        this.z2 = z2;
        this.x3 = x3;
        this.z3 = z3;
        this.x4 = x4;
        this.z4 = z4;

        this.corner1 = new Location(this.world, this.x1, 0, this.z1);
        this.corner2 = new Location(this.world, this.x2, world.getMaxHeight(), this.z2);
        this.corner3 = new Location(this.world, this.x3, 0, this.z3);
        this.corner4 = new Location(this.world, this.x4, world.getMaxHeight(), this.z4);
    }

    public ArrayList<Location> getCorners() {
        ArrayList<Location> corners = new ArrayList<Location>();
        corners.add(this.corner1);
        corners.add(this.corner2);
        corners.add(this.corner3);
        corners.add(this.corner4);
        return corners;
    }

    public void addAffectedBlockPosition(Location block) {
        this.affected_blocks.add(block);
    }

    public void removeAffectedBlockPosition(Location block){
        for (int i = 0; i < affected_blocks.size(); i++) {
            if (affected_blocks.get(i).equals(block)) {
                affected_blocks.remove(i);
                break;
            }
        }
    }

    public ArrayList<Location> getAffectedBlocks() {
        return affected_blocks;
    }
}
