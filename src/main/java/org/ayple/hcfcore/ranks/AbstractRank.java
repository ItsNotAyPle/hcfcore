package org.ayple.hcfcore.ranks;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public abstract class AbstractRank {
    public abstract String getRankName();
    public abstract ChatColor getPrefixColor();
    public abstract String getRankPrefix();
    public abstract ArrayList<String> getPermissions();

}
