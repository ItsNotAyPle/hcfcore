package org.ayple.hcfcore.core.cooldowns.newcooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.UUID;


//TODO: there is acc an issue where if a player logs out and a second player is loading
// the chunks then if the second player logs out too then both villagers will just
// stay alive but they won't acc drop items or count towards player death just
// dont get despawned
public class LogoutVillagerTimer extends AbstractCooldown {
    UUID ownerID;
    Villager villager;
    Inventory inventory;

    public LogoutVillagerTimer(Player p) {
        super(p, 15, "logout_villager");
        this.ownerID = p.getUniqueId();
        this.inventory = p.getInventory();
        this.villager = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
        this.villager.setCustomName(ChatColor.RED + p.getDisplayName());
        this.villager.setMetadata("LOGOUT VILLAGER", new FixedMetadataValue(Hcfcore.getInstance(), true));
        this.villager.setMetadata("OWNER UUID", new FixedMetadataValue(Hcfcore.getInstance(), ownerID));
        this.villager.setRemoveWhenFarAway(true);
    }

    @Override
    public void onTimerChanged() {
        this.villager.getLocation().getChunk().load(true);
    }

    @Override
    public void onTimerFinished() {
        if (CooldownManager.hasLogoutVillager(this.ownerID)) {
            CooldownManager.onLogoutVillagerTimeOver(this.ownerID);
        }

        if (villager != null) {
            this.villager.getLocation().getChunk().load(true);
            villager.remove();
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}

