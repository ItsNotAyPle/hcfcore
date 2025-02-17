package org.ayple.hcfcore.commands;


import org.ayple.hcfcore.Hcfcore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

// https://www.youtube.com/watch?v=WyFN_jTS4nU
public abstract class SubCommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract void perform(Player player, String[] args);

    public void runCommand(Player player, String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                perform(player, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
