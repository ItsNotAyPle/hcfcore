package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.kits.BardClass;
import org.ayple.hcfcore.kits.kitmap.KitmapManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class BardEffectsEvent implements Listener {
    @EventHandler
    public void onPlayerHoldItem(PlayerItemHeldEvent event) {
        Player p = event.getPlayer();

        // TODO: for the love of god, optimize this eventually,
        // TODO: test this!!


        // this is done here to avoid errors
        if (!KitmapManager.wearingAllArmor(p.getInventory())) {
            return;
        }

        PlayerInventory inventory = p.getInventory();
        ItemStack helmet = inventory.getHelmet();
        ItemStack chestplate = inventory.getChestplate();
        ItemStack leggings = inventory.getLeggings();
        ItemStack boots = inventory.getBoots();

        if (helmet.getType() != Material.GOLD_HELMET ||
                chestplate.getType() != Material.GOLD_CHESTPLATE ||
                leggings.getType() != Material.GOLD_LEGGINGS ||
                boots.getType() != Material.GOLD_BOOTS) return;


        Faction player_faction = NewFactionManager.getFactionFromPlayerID(p.getUniqueId());
        if (player_faction == null) {
            return;
        }


//        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
                for (Entity ps : p.getNearbyEntities(10, 20, 10)) {
                    if (ps instanceof Player) {
                        Player target_player = (Player) ps;
                        for (UUID member_id : player_faction.getFactionMembers().keySet()) {
                            if (member_id == target_player.getUniqueId()) {
                                BardClass.giveEffectToTargetPlayer(p.getInventory().getItemInHand().getType(), target_player);
                                BardClass.giveEffectToTargetPlayer(p.getInventory().getItemInHand().getType(), p);
                            }
                        }
                    }

                }
//            });
    }
}
