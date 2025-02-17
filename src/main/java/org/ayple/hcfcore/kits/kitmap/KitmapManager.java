package org.ayple.hcfcore.kits.kitmap;

import org.ayple.hcfcore.kits.kitmap.kits.ArcherKit;
import org.ayple.hcfcore.kits.kitmap.kits.BardKit;
import org.ayple.hcfcore.kits.kitmap.kits.DiamondKit;
import org.ayple.hcfcore.kits.kitmap.kits.MinerKit;
import org.bukkit.inventory.PlayerInventory;

public class KitmapManager {
    public static DiamondKit diamondkit = new DiamondKit();
    public static ArcherKit archerkit = new ArcherKit();
    public static BardKit bardkit = new BardKit();
    public static MinerKit minerkit = new MinerKit();


    public static boolean wearingAllArmor(PlayerInventory armor) {
        return (armor.getHelmet() != null &&
                armor.getChestplate() != null &&
                armor.getLeggings() != null &&
                armor.getBoots() != null);
    }
}
