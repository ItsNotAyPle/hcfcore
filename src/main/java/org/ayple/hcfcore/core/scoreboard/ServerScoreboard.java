package org.ayple.hcfcore.core.scoreboard;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

public class ServerScoreboard {
    private final static String SERVER_NAME = ConfigHelper.getConfig().getString("server_name");
    private final static String MAP_NUMBER = ConfigHelper.getConfig().getString("map_number");




    public static Scoreboard newScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("scoreboard", "dummy");

        if (Hcfcore.getInstance().serverInKitmapMode()) {
            objective.setDisplayName(ChatColor.GOLD + "     " + SERVER_NAME + " [KITMAP]");
        } else {
            objective.setDisplayName(ChatColor.GOLD + "     " + SERVER_NAME + " [Map " + MAP_NUMBER + "]     ");
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("").setScore(0);



        return board;
    }
}
