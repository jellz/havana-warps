package com.danielgulic.havanawarps;

/**
 * Copyright (c) 2019
 * Daniel Gulic
 */

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.md_5.bungee.api.ChatColor;

import java.util.Set;

public class WarpsInventory implements InventoryProvider {
    @Override
    public void init(Player player, InventoryContents contents) {
        Set<String> warpsList = HavanaWarps.get().getConfig().getConfigurationSection("warps").getKeys(false);
        ConfigurationSection warpsSection = HavanaWarps.get().getConfig().getConfigurationSection("warps");
        int i = 0;
        for (String warpName : warpsList) {
            ConfigurationSection warpSection = warpsSection.getConfigurationSection(warpName);
            contents.set(0, i, ClickableItem.of(
                    new ItemBuilder(Material.matchMaterial(warpSection.getString("item")), 1, (byte) 0).name(ChatColor.GREEN + warpSection.getString("name")).make(),
                    e -> {
                        YamlConfiguration config = PlayerConfig.getOrCreateConfig(player.getUniqueId().toString());
                        if (!config.getBoolean("can-use-warp")) {
                            player.sendMessage(ChatColor.RED + "You've already used your outpost warp!");
                            player.closeInventory();
                        } else {
                            Location loc = new Location(player.getWorld(), warpSection.getDouble("x-coord"), warpSection.getDouble("y-coord"), warpSection.getDouble("z-coord"), Float.parseFloat(warpSection.getString("facing")), 0);
                            player.teleport(loc);
                            PlayerConfig.set(player.getUniqueId().toString(), "can-use-warp", false);
                            player.sendMessage(ChatColor.GOLD + "Teleported to " + warpSection.getString("name") + " outpost!");
                        }
                    }
            ));
            i++;
        }

        // Close
        contents.set(0, 8,
                ClickableItem.of(
                        new ItemBuilder(Material.BARRIER)
                                .name(ChatColor.DARK_RED + "Close")
                                .make(),
                        e -> player.closeInventory()
                )
        );
    }

    @Override
    public void update(Player player, InventoryContents contents) {}
}