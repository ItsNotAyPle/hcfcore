package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DtrEventHandler implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (Hcfcore.getInstance().serverInKitmapMode()) return;

        Player player = (Player) event.getEntity();
        Faction player_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
        if (player_faction != null) {
            CooldownManager.registerDtrRegenTimer(player_faction);
        }



    }
}
