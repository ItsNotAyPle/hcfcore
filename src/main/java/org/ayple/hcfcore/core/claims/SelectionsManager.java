package org.ayple.hcfcore.core.claims;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.UUID;


// this currently only supports claims

// this probably wont be used as of 09/07/23
public class SelectionsManager {
    private static Hashtable<UUID, Selection> selections = new Hashtable<UUID, Selection>();


    public static void registerSelection(UUID id, Selection selection) {
        selections.put(id, selection);
    }

    public static void registerSelection(UUID id, Location corner_1, Location corner_2) {
        selections.put(id, new Selection(corner_1, corner_2));
    }


    // NO LONGER USED DUE TO NEW PILLAR METHOD - 09/07/23
    public static void setPos1(Player player, Location corner_location) {
        UUID player_id = player.getUniqueId();
        Selection selection = getAnyExistingSelection(player_id);
        if (selection == null) {
            selection = new Selection(corner_location, null);
            selections.put(player_id, selection);
        }

        selection.setPos1(corner_location);
    }

    // NO LONGER USED DUE TO NEW PILLAR METHOD - 09/07/23
    public static void setPos2(Player player, Location corner_location) {
        UUID player_id = player.getUniqueId();
        Selection selection = getAnyExistingSelection(player_id);
        if (selection == null) {
            selection = new Selection(null, corner_location);
            selections.put(player_id, selection);
        }

        selection.setPos2(corner_location);

    }

    // putting this in it's own function incase changes
    // are made in the future
    public static Selection getAnyExistingSelection(UUID id) {
        return selections.get(id);
    }

    public static void clearAnySelectionPlayerHas(Player player) {
        ClaimPillarManager.removeCorner1Pillar(player);
        ClaimPillarManager.removeCorner2Pillar(player);
        selections.remove(player.getUniqueId());
    }

    public static Selection getSelection(Player player) {
        return selections.get(player.getUniqueId());
    }

}
