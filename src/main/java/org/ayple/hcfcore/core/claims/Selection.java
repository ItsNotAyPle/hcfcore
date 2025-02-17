package org.ayple.hcfcore.core.claims;

import org.ayple.hcfcore.core.Cuboid;
import org.bukkit.Location;

import java.util.Hashtable;
import java.util.UUID;

public class Selection {
    private Location pos1;
    private Location pos2;
    private Cuboid cuboid = null;

    public Selection(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        checkForCuboid();

    }

    public Selection(Cuboid cuboid) {
        this.cuboid = cuboid;
        this.pos1 = this.cuboid.getLowerNE();
        this.pos2 = this.cuboid.getUpperSW();
    }

    public Location getPos1() {
        return this.pos1;
    }
    public Location getPos2() {
        return this.pos2;
    }

    public Cuboid getCuboid() {
        checkForCuboid();
        return this.cuboid;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
        checkForCuboid();
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
        checkForCuboid();
    }

    private void checkForCuboid() {
        if (this.getPos1() != null && this.getPos2() != null) {
            this.cuboid = new Cuboid(this.getPos1(), this.getPos2());
            return;
        }

        this.cuboid = null;
    }

    // only checks cuboid, not stuff like if player is
    // in a faction or not.
    public static void isCuboidClaimLegal() {

    }
}
