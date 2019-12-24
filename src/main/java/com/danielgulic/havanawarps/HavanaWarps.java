package com.danielgulic.havanawarps;

/**
 * Copyright (c) 2019
 * Daniel Gulic
 */

import fr.minuskube.inv.SmartInventory;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HavanaWarps extends JavaPlugin implements CommandExecutor {

    public static HavanaWarps instance;
    public static HavanaWarps get() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
        getCommand("warps").setExecutor(this);

        FileConfiguration fileConfiguration = getConfig();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() { instance = null; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("warps")) {
                final SmartInventory inventory = SmartInventory.builder()
                        .id("warpsMenu")
                        .provider(new WarpsInventory())
                        .size(1, 9)
                        .closeable(true)
                        .title(ChatColor.DARK_AQUA + "Warp to an outpost")
                        .build();

                inventory.open(player);

                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        return false;
    }
}
