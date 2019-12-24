package com.danielgulic.havanawarps;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Player Config class
 * for simple player data YAML storage
 * Copyright 2019 Daniel Gulic
 */

public class PlayerConfig {
    public static void set(String id, String key, Object value) {
        YamlConfiguration config = getOrCreateConfig(id);
        config.set(key, value);
        try {
            config.save(getConfigFile(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfiguration getOrCreateConfig(String id) {
        File dataFolder = HavanaWarps.get().getDataFolder();
        File configFile = new File(dataFolder, id + ".yml");
        YamlConfiguration config = null;
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                config = YamlConfiguration.loadConfiguration(configFile);
                // -- CONFIG DEFAULTS --
                config.set("can-use-warp", true);
                // ---------------------
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            config = YamlConfiguration.loadConfiguration(configFile);
        }

        return config;
    }

    private static File getConfigFile(String id) {
        return new File(HavanaWarps.get().getDataFolder(), id + ".yml");
    }
}
