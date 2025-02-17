package org.ayple.hcfcore.core.scoreboard;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;


// if anyone reads this in like a leak or smthn idfk, this helped out so much
// https://github.com/IPVP-MC/iHCF/blob/master/core/src/main/java/com/doctordark/hcf/scoreboard/PlayerBoard.java

public class PlayerBoard {

    private boolean sidebarVisible = false;
    private final AtomicBoolean removed = new AtomicBoolean(false);
    private final Team members;
    private final Team neutrals;
    private final Team allies;
    private final UUID ownerID;
    private final Scoreboard scoreboard;
    private BufferedObjective bufferedObjective;
    private SidebarProvider defaultProvider;
    private SidebarProvider temporaryProvider;
    public BukkitRunnable runnable;

    public PlayerBoard(Player player) {
        this.ownerID = player.getUniqueId();


        this.scoreboard = Hcfcore.getInstance().getServer().getScoreboardManager().getNewScoreboard();
        this.bufferedObjective = new BufferedObjective(this.scoreboard, ChatColor.GOLD + "CheekyHCF [Map 1]");

        this.members = scoreboard.registerNewTeam("members");
        this.members.setPrefix(ChatColor.GREEN.toString());
        this.members.setCanSeeFriendlyInvisibles(true);

        this.neutrals = scoreboard.registerNewTeam("neutrals");
        this.neutrals.setPrefix(ChatColor.YELLOW.toString());

        this.allies = scoreboard.registerNewTeam("allies");
        this.allies.setPrefix(ChatColor.BLUE.toString());

        player.setScoreboard(this.scoreboard);
    }

    public void remove() {
        if (this.scoreboard != null) {
            for (Team team: this.scoreboard.getTeams()) {
                team.unregister();
            }

            for (Objective objective: this.scoreboard.getObjectives()) {
                objective.unregister();
            }
        }
    }

    public void setSidebarVisible(boolean visible) {
        this.sidebarVisible = visible;
        this.bufferedObjective.setDisplaySlot(visible ? DisplaySlot.SIDEBAR : null);
    }

    public void setDefaultSidebar(final SidebarProvider provider, long updateInterval) {
        if (provider != this.defaultProvider) {
            this.defaultProvider = provider;
            if (this.runnable != null) {
                this.runnable.cancel();
            }

            if (provider == null) {
                this.scoreboard.clearSlot(DisplaySlot.SIDEBAR);
                return;
            }

            (this.runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    if (removed.get()) {
                        cancel();
                        return;
                    }

                    if (provider == defaultProvider) {
                        updateObjective();
                    }
                }
            }).runTaskTimerAsynchronously(Hcfcore.getInstance(), updateInterval, updateInterval);
        }
    }

    public void setTemporarySidebar(final SidebarProvider provider, final long expiration) {
        if (this.removed.get()) {
            throw new IllegalStateException("Cannot update whilst board is removed");
        }

        this.temporaryProvider = provider;
        this.updateObjective();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (removed.get()) {
                    cancel();
                    return;
                }

                if (temporaryProvider == provider) {
                    temporaryProvider = null;
                    updateObjective();
                }
            }
        }.runTaskLaterAsynchronously(Hcfcore.getInstance(), expiration);
    }

    private void updateObjective() {
        if (this.removed.get()) {
            throw new IllegalStateException("Cannot update whilst board is removed");
        }


        SidebarProvider provider = this.temporaryProvider != null ? this.temporaryProvider : this.defaultProvider;
        if (provider == null) {
            this.bufferedObjective.setVisible(false);
        } else {
            OfflinePlayer player = Bukkit.getOfflinePlayer(ownerID);
            if (player.isOnline()) {
                this.bufferedObjective.setAllLines(provider.getLines(player.getPlayer()));
                this.bufferedObjective.flip();
            }
        }
    }

    public void addUpdate(Player target) {
        this.addUpdates(Collections.singleton(target));
    }

    public void addUpdates(Iterable<? extends Player> updates) {
        if (this.removed.get()) {
            throw new IllegalStateException("Cannot update whilst board is removed");
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (removed.get()) {
                    cancel();
                    return;
                }


                Player player = Bukkit.getPlayer(ownerID);

                // Lazy load - don't lookup this in every iteration
                Faction playerFaction = null;
                boolean firstExecute = false;
                //

                for (Player update : updates) {
                    if (player == null) continue;

                    if (player.equals(update)) {
                        members.addPlayer(update);
                        continue;
                    }

                    if (!firstExecute) {
                        playerFaction = NewFactionManager.getFactionFromPlayerID(ownerID);
                        firstExecute = true;
                    }

                    // Lazy loading for performance increase.
                    Faction targetFaction;
                    if (playerFaction == null || (targetFaction = NewFactionManager.getFactionFromPlayerID(update.getUniqueId())) == null) {
                        neutrals.addPlayer(update);
                    } else if (playerFaction == targetFaction) {
                        members.addPlayer(update);
//                    } else if (playerFaction.getAllied().contains(targetFaction.getUniqueID())) {
//                        allies.addPlayer(update);
                    } else {
                        neutrals.addPlayer(update);
                    }
                }
            }
        }.runTaskAsynchronously(Hcfcore.getInstance());
    }
}
