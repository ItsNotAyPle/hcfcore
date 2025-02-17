package org.ayple.hcfcore.ranks;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public class OwnerRank extends AbstractRank {

    @Override
    public String getRankName() {
        return "Owner";
    }

    @Override
    public ChatColor getPrefixColor() {
        return ChatColor.RED;
}

    @Override
    public String getRankPrefix() {
        return getPrefixColor() + getRankName() + ChatColor.RESET;
    }

    @Override
    public ArrayList<String> getPermissions() {
        ArrayList<String> permissions = new ArrayList<String>();
        permissions.add("hcf.core.kit.diamond");
        permissions.add("hcf.core.kit.archer");
        permissions.add("hcf.core.kit.bard");
        permissions.add("hcf.core.kit.miner");
        permissions.add("hcf.core.kit.starter");
        permissions.add("hcf.core.owner");
        return permissions;
    }
}
