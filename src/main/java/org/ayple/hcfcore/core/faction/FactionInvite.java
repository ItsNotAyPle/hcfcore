package org.ayple.hcfcore.core.faction;

import java.util.Date;
import java.util.UUID;

public class FactionInvite {
    public UUID faction_id;
    public UUID player_id;

    public FactionInvite(UUID faction_id, UUID player_id) {
        this.faction_id = faction_id;
        this.player_id = player_id;
    }
}
