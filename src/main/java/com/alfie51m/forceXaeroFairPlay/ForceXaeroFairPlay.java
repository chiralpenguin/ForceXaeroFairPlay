package com.alfie51m.forceXaeroFairPlay;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ForceXaeroFairPlay extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("ForceXaeroFairPlay Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ForceXaeroFairPlay Plugin has been disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();

        String tellrawCommand = String.format("tellraw %s \"\\u00a7f\\u00a7a\\u00a7i\\u00a7r\\u00a7x\\u00a7a\\u00a7e\\u00a7r\\u00a7o\"", playerName);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), tellrawCommand);
    }
}
