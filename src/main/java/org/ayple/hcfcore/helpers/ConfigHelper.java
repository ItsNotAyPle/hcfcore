package org.ayple.hcfcore.helpers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigHelper {

    private static File file;
    private static FileConfiguration configFile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("hcfcore").getDataFolder(), "hcfcore.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {

            }

        }

        reload();

    }

    public static FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void reload() {
        configFile = YamlConfiguration.loadConfiguration(file);
    }

    public static void save() {
        try {
            configFile.save(file);
        } catch (IOException e) {
            System.out.println("Failed to save file!");
        }
    }

}
