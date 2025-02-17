package org.ayple.hcfcore.core.cooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.newcooldowns.*;
import org.ayple.hcfcore.core.faction.Faction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;

import java.util.*;
import java.util.logging.Level;

public class CooldownManager {

    // UUID is player id //

    private static final Hashtable<UUID, EnderpearlCooldown> enderpearlCooldowns = new Hashtable<UUID, EnderpearlCooldown>();
    private static final Hashtable<UUID, HomeTimer> homeTimers = new Hashtable<UUID, HomeTimer>(); // f home
    private static final Hashtable<UUID, CombatTimer> combatCooldowns = new Hashtable<UUID, CombatTimer>(); // combat timers
    private static final Hashtable<UUID, LogoutTimer> logoutTimer = new Hashtable<UUID, LogoutTimer>(); // logout timers
    private static final Hashtable<UUID, PvpTimer> pvpTimers = new Hashtable<UUID, PvpTimer>(); // safe pvp timers
    private static final Hashtable<UUID, DtrRegenTimer> dtrRegenTimers = new Hashtable<UUID, DtrRegenTimer>();
    private static final Hashtable<UUID, CrappleCooldown> crappleCooldowns = new Hashtable<UUID, CrappleCooldown>();
    private static final Hashtable<UUID, LogoutVillagerTimer> logoutVillagers = new Hashtable<UUID, LogoutVillagerTimer>();
    private static final HashSet<UUID> logoutVillagersDead = new HashSet<UUID>();

    public static void addDeadLogoutVillager(UUID player_id) {
        logoutVillagersDead.add(player_id);
    }

    public static boolean UUIDInLogoutVillagerDiedSet(UUID player_id) {
        return logoutVillagersDead.contains(player_id);
    }

    public static void removeDeadLogoutVillager(UUID player_id) {
        logoutVillagersDead.remove(player_id);
    }

    public static void registerLogoutVillager(Player owner) {
        logoutVillagers.put(owner.getUniqueId(), new LogoutVillagerTimer(owner));
    }

    public static void cancelLogoutVillager(UUID id) {
        LogoutVillagerTimer timer = logoutVillagers.get(id);
        if (timer == null) return;

        timer.onTimerFinished();
        timer.cancel();

    }

    public static Inventory getLogoutVillagerInventoryData(UUID id) {
        return logoutVillagers.get(id).getInventory();
    }

    public static boolean hasLogoutVillager(UUID id) {
        return logoutVillagers.containsKey(id);
    }

    public static void onLogoutVillagerTimeOver(UUID id) {
        logoutVillagers.remove(id);
    }

    public static void registerCrappleCooldown(Player owner) {
        crappleCooldowns.put(owner.getUniqueId(), new CrappleCooldown(owner));
    }

    public static void onFinishedCrappleCooldown(UUID owner) {
        crappleCooldowns.remove(owner);
    }

    public static boolean hasCrappleCooldown(UUID player_id) {
        return crappleCooldowns.containsKey(player_id);
    }

    public static int getSecondsLeftOfCrappleCooldown(UUID player_id) {
        return crappleCooldowns.get(player_id).getSecondsLeft();
    }

    public static void registerDtrRegenTimer(Faction faction) {
        dtrRegenTimers.put(faction.getFactionID(), new DtrRegenTimer(faction));
    }

    public static void onFinishedDtrRegen(Faction faction) {
        if (hasDtrRegen(faction)) {
            dtrRegenTimers.get(faction.getFactionID()).seconds_left = 0;
            dtrRegenTimers.remove(faction.getFactionID());
            faction.setFactionDTR(faction.getMaxDTR());
        }
    }

    public static boolean hasDtrRegen(Faction faction) {
        return dtrRegenTimers.containsKey(faction.getFactionID());
    }

    public static int getDtrRegenSecondLeft(Faction faction) {
        return dtrRegenTimers.get(faction.getFactionID()).getSecondsLeft();
    }

    public static void registerPvpTimer(Player player) {
        pvpTimers.put(player.getUniqueId(), new PvpTimer(player));
    }

    public static void onFinishedPvpTimer(UUID player_id) {
        pvpTimers.remove(player_id);
    }

    public static boolean playerHasPvpTimer(UUID player_id) {
        return pvpTimers.get(player_id) != null;
    }


    public static void cancelPvpTimer(UUID player_id) {
        pvpTimers.get(player_id).cancel();
        pvpTimers.remove(player_id);
    }

    public static int getSecondsLeftOfPvpTimer(UUID player_id) {
        if (playerHasPvpTimer(player_id))
            return pvpTimers.get(player_id).getSecondsLeft();
        return 0;
    }

    public static void registerEnderpearlCooldown(Player player) {
        enderpearlCooldowns.put(player.getUniqueId(), new EnderpearlCooldown(player));
    }

    public static int getSecondsLeftOfEnderpearlCooldown(UUID player_id) {
        EnderpearlCooldown cooldown = enderpearlCooldowns.get(player_id);
        if (cooldown == null) {
            Hcfcore.getInstance().getLogger().log(Level.SEVERE, "Failed to get the seconds left of enderpearl cooldown?");
            return 0;
        }

        return cooldown.getSecondsLeft();

    }



    public static void onFinishedEnderpearlCooldown(UUID player_id) {
        enderpearlCooldowns.remove(player_id);
    }

    public static boolean hasEnderpearlCooldown(UUID player_id) {
        return enderpearlCooldowns.containsKey(player_id);
    }

    public static void registerHomeTimer(Player player, Location hq) {
        homeTimers.put(player.getUniqueId(), new HomeTimer(player, hq));
    }

    public static void onFinishedHomeTimer(UUID player_id) {
        homeTimers.remove(player_id);
    }

    public static void cancelHomeTimer(UUID player_id) {
        HomeTimer timer = homeTimers.get(player_id);
        if (timer == null) return;
        timer.cancel();
        homeTimers.remove(player_id);
    }

    public static boolean hasHomeTimer(UUID player_id) {
        return homeTimers.containsKey(player_id);
    }

    public static int getSecondsLeftOfHomeTimer(UUID player_id) {
        return homeTimers.get(player_id).getSecondsLeft();
    }

    public static void registerCombatTimer(Player player) {
        if (combatCooldowns.containsKey(player.getUniqueId()))
            combatCooldowns.get(player.getUniqueId()).cancel();

        combatCooldowns.put(player.getUniqueId(), new CombatTimer(player));
    }

    public static void onCombatTimerOver(UUID player_id) {
        combatCooldowns.remove(player_id);
    }

    public static boolean hasCombatTimer(UUID player_id) {
        return combatCooldowns.containsKey(player_id);
    }

    public static int getSecondsLeftOfCombatTimer(UUID player_id) {
        return combatCooldowns.get(player_id).getSecondsLeft();
    }

    public static void registerLogoutTimer(Player player) {
        logoutTimer.put(player.getUniqueId(), new LogoutTimer(player));
    }

    public static int getSecondsLeftOfLogoutTimer(Player player) {
        return logoutTimer.get(player.getUniqueId()).getSecondsLeft();
    }

    public static void onLogoutTimerDone(UUID player_id) {
        logoutTimer.remove(player_id);
    }

    public static boolean hasLogoutTimer(UUID player_id) {
        return logoutTimer.containsKey(player_id);
    }

    public static void cancelLogoutTimer(UUID player_id) {
        if (!hasLogoutTimer(player_id)) return;
        logoutTimer.get(player_id).cancel();
        logoutTimer.remove(player_id);
    }



}
