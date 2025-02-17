package org.ayple.hcfcore.core.claims.map;

import javax.xml.stream.Location;
import java.util.UUID;

public class MapPillar {

    public final Location corner_1, corner_2, corner_3, corner_4;
    public final UUID player_id;


    public MapPillar(UUID player_id, Location corner_1, Location corner_2, Location corner_3, Location corner_4) {
        this.player_id = player_id;
        this.corner_1 = corner_1;
        this.corner_2 = corner_2;
        this.corner_3 = corner_3;
        this.corner_4 = corner_4;
    }
}
