package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.Cuboid;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.claims.map.ClaimMap;
import org.ayple.hcfcore.core.claims.map.MapPillarsHandler;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandFactionMap extends SubCommand {
    @Override
    public String getName() {
        return "map";
    }

    @Override
    public String getDescription() {
        return "display the claim corners";
    }

    @Override
    public String getSyntax() {
        return "/f map";
    }

    @Override
    public void perform(Player player, String[] args) {
        ArrayList<Cuboid> claims =  ClaimsManager.getAllClaimCuboids();
        Location location = player.getLocation();
        Location c1 = new Location(player.getWorld(), location.getX() + 100,  0, location.getZ() + 100);
        Location c2 = new Location(player.getWorld(), location.getX() - 100,  0, location.getZ() - 100);
        Cuboid radius = new Cuboid(c1, c2);


        //TODO: maybe try look for another solution?
        // for each claim -> loop through all the blocks and check if
        // the block is in range of region
        for (Cuboid claim : claims) {
            Cuboid claim_surface = ClaimsManager.cuboid3Dto2D(claim);

            for (Block block : claim_surface.getBlocks()) {
                if (radius.contains(block.getLocation())) {
                    ClaimMap corners = getClaimMap(player, claim);
                    MapPillarsHandler.showClaimCorners(player, corners);
                }
            }

        }
    }

    private static ClaimMap getClaimMap(Player player, Cuboid claim) {
        Location lower_corner = claim.getLowerNE();
        Location upper_corner = claim.getUpperSW();

        // get each corner of the cuboid basically
        ClaimMap corners = new ClaimMap(player.getWorld(),
                lower_corner.getX(), lower_corner.getZ(),
                lower_corner.getX(), upper_corner.getZ(),
                upper_corner.getX(), upper_corner.getZ(),
                upper_corner.getX(), lower_corner.getZ()
        );
        return corners;
    }


}
