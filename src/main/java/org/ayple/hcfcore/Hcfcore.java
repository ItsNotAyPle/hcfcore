package org.ayple.hcfcore;

import net.milkbowl.vault.economy.Economy;
import org.ayple.hcfcore.commands.*;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.claims.serverclaim.SpawnClaim;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.core.scoreboard.ScoreboardHandler;
import org.ayple.hcfcore.events.*;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;


// NOTE: disabled event listeners  and command are most likely due to
// using other plugins instead like essentials

public final class Hcfcore extends JavaPlugin {
    private static Hcfcore INSTANCE;

    public static Hcfcore getInstance() {
        return INSTANCE;
    }

    private static Economy economy = null;
    public boolean KITMAP_MODE = true;

    private int FACTION_SIZE_LIMIT = 5;

    private boolean SOTW_MODE = false;

    private final ScoreboardHandler SCOREBOARD_HANDLER = new ScoreboardHandler();



    @Override
    public void onEnable() {
        INSTANCE = this;
        System.out.println("Starting up HCF core!");
        ConfigHelper.setup();

        ConfigHelper.getConfig().addDefault("DB_HOST", "127.0.0.1");
        ConfigHelper.getConfig().addDefault("DB_PORT", "3006");
        ConfigHelper.getConfig().addDefault("DB_USER", "root");
        ConfigHelper.getConfig().addDefault("DB_PASS", "password");
        ConfigHelper.getConfig().addDefault("DB_NAME", "hcf");
        ConfigHelper.getConfig().addDefault("server_name", "cheeky hcf");
        ConfigHelper.getConfig().addDefault("map_number", 1);
        ConfigHelper.getConfig().addDefault("kitmap_mode", false);
        ConfigHelper.getConfig().addDefault("enchant_limits.sharpness", 2);
        ConfigHelper.getConfig().addDefault("enchant_limits.protection", 2);
        ConfigHelper.getConfig().addDefault("enchant_limits.enchant_limits.power", 4);
        ConfigHelper.getConfig().addDefault("end_portal_exit.x", 1);
        ConfigHelper.getConfig().addDefault("end_portal_exit.y", 80);
        ConfigHelper.getConfig().addDefault("end_portal_exit.z", 200);
        ConfigHelper.getConfig().addDefault("server_claims.spawn.spawn_corner_1_x", -50);
        ConfigHelper.getConfig().addDefault("server_claims.spawn.spawn_corner_1_z", 50);
        ConfigHelper.getConfig().addDefault("server_claims.spawn.spawn_corner_2_x", 50);
//        ConfigHelper.getConfig().addDefault("server_claims.glowstone_mountain.glowstone_respawn_area", -50);


        ConfigHelper.getConfig().options().copyDefaults(true);
        ConfigHelper.save();

        KITMAP_MODE = ConfigHelper.getConfig().getBoolean("kitmap_mode");
        FACTION_SIZE_LIMIT = ConfigHelper.getConfig().getInt("faction_limit");



        if (!setupEconomy()) {
            System.out.println("Disabled due to no Vault dependency!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }





        try {
            HcfSqlConnection.createDatabase();
            NewFactionManager.loadFactions();
            PlayerDataHandler.loadAllPlayerData();
            ClaimsManager.reloadClaims();
            NewFactionManager.applyDTRregens();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        registerCommands();
        registerEvents();

        //GlowstoneMountainClaim glowstoneMountainClaim = new GlowstoneMountainClaim();
        new SpawnClaim(); // register spawn claim


    }

    private void registerCommands() {
        getCommand("faction").setExecutor(new CommandFaction());
        getCommand("logout").setExecutor(new CommandLogout());
        getCommand("balance").setExecutor(new CommandBalance());
        getCommand("pvpenable").setExecutor(new CommandPvpEnable());
        getCommand("lives").setExecutor(new CommandLives());
        getCommand("revive").setExecutor(new CommandRevive());
        getCommand("staff").setExecutor(new CommandStaff());
        getCommand("msg").setExecutor(new CommandMsg());
//        getCommand("kit").setExecutor(new CommandKit());
        //getCommand("serverclaim").setExecutor(new CommandServerClaim());

    }

    public void registerEvents() {


        PluginManager manager =  getServer().getPluginManager();
        manager.registerEvents(new AntiGriefEvent(), this);
        manager.registerEvents(new BardEffectsEvent(), this);
        manager.registerEvents(new BuyLifeSignEvent(), this);
        manager.registerEvents(new ClaimWandEvent(), this);
        manager.registerEvents(new DtrEventHandler(), this);
        manager.registerEvents(new ElevatorEventListener(), this);
        manager.registerEvents(new EnchantLimiterEvent(), this);
        manager.registerEvents(new EndEventHandler(), this);
        manager.registerEvents(new GoldenAppleListenerEvent(), this);
        manager.registerEvents(new LogoutVillagerEvent(), this);
//        manager.registerEvents(new OnClickKitGUIEvent(), this);
        manager.registerEvents(new OnEnderPearlEvent(), this);
        manager.registerEvents(new OnPlayerMoveEvent(), this);
        manager.registerEvents(new OnSleepEvent(), this);
        manager.registerEvents(new PlayerArmorChangeEvent(), this);
        manager.registerEvents(new PlayerDeathBanEvent(), this);
        manager.registerEvents(new PlayerHitEvent(), this);
        manager.registerEvents(new PlayerInteractEntity(), this);
        manager.registerEvents(new PlayerJoinedServerEvent(), this);
        manager.registerEvents(new PlayerLeaveServerEvent(), this);
        manager.registerEvents(new PlayerUseChatEvent(), this);
        manager.registerEvents(new PotionRefillSignEvent(), this);
        manager.registerEvents(new PvpTimerEvent(), this);
        manager.registerEvents(new SpawnListenerEvent(), this);
        manager.registerEvents(new TntDisablerEvent(), this);
    }

    @Override
    public void onDisable() {
    }

    public ScoreboardHandler getScoreboardHandler() {
        return this.SCOREBOARD_HANDLER;
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        

        return (economy != null);
    }

    public static Economy getEconomy() {
        return economy;
    }


    public void broadcastMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public void broadcastPlayerMessage(Player sender, String message) {
        Faction sender_faction = NewFactionManager.getFactionFromPlayerID(sender.getUniqueId());

        // if no faction then just send a message to every player as yellow name
        // else check the factions, shouldn't be too bad on the optmization
        // point but oh well.
        if (sender_faction == null) {
            for (Player player: Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.YELLOW + sender.getDisplayName() + ": " + ChatColor.RESET + message);
            }

            return;
        }

        // this could probably be better optimized if instead of looping through
        // ever player it just sends the green ones to the faction members then
        // broadcast yellow to everyone else. But shouldn't be a massive problem.
        // plus this allows support for ally factions n shit
        for (Player player: Bukkit.getOnlinePlayers()) {
            Faction player_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (player_faction != null) {
                // if in same faction
                if (sender_faction.getFactionID() == player_faction.getFactionID()) {
                    player.sendMessage(ChatColor.GREEN + "[" + sender_faction.getFactionName() + "] " + ChatColor.YELLOW + sender.getDisplayName() + ": " + ChatColor.RESET + message);
                } else {
                    player.sendMessage(ChatColor.YELLOW + "[" + sender_faction.getFactionName() + "] " + ChatColor.YELLOW + sender.getDisplayName() + ": " + ChatColor.RESET + message);
                }
            }


        }
    }

    public int getTeamLimitSize() {
        return this.FACTION_SIZE_LIMIT;
    }

    public void enableSOTWMode() {
        this.SOTW_MODE = true;
    }

    public void disableSOTWMode() {
        this.SOTW_MODE = false;
    }

    public int getSharpnessEnchantLimit() { return ConfigHelper.getConfig().getInt("enchant_limits.sharpness"); }
    public int getProtectionEnchantLimit() { return ConfigHelper.getConfig().getInt("enchant_limits.protection"); }
    public int getPowerEnchantLimit() { return ConfigHelper.getConfig().getInt("enchant_limits.power"); }

    public boolean serverInSOTWMode() { return this.SOTW_MODE; }
    public int getSOTWSecondsLeft() { return 0; }

    public boolean serverInKitmapMode() { return this.KITMAP_MODE; }

}
