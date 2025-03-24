package com.alfie51m.forceXaeroFairPlay;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ForceXaeroFairPlay extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ForceXaeroFairPlay has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ForceXaeroFairPlay has been disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        handlePlayerMode(player, player.getWorld().getName(), null);
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String fromWorld = event.getFrom().getName();
        String toWorld = player.getWorld().getName();

        handlePlayerMode(player, toWorld, fromWorld);
    }

    private void handlePlayerMode(Player player, String toWorldName, String fromWorldName) {
        if (player.hasPermission("forcexaerofairplay.bypass")) {
            return;
        }

        FileConfiguration config = getConfig();
        String defaultMode = config.getString("defaultMode", "none").toLowerCase();
        String toWorldMode = config.getString("worldModes." + toWorldName, defaultMode).toLowerCase();
        String fromWorldMode = fromWorldName != null
                ? config.getString("worldModes." + fromWorldName, defaultMode).toLowerCase()
                : "none";

        StringBuilder messageBuilder = new StringBuilder();

        if (!fromWorldMode.equals(toWorldMode) && (toWorldMode.equals("none") || !toWorldMode.equals("none"))) {
            messageBuilder.append("§r§e§s§e§t§x§a§e§r§o ");
        }

        switch (toWorldMode) {
            case "fairplay":
                messageBuilder.append("§f§a§i§r§x§a§e§r§o");
                break;

            case "disabled":
                messageBuilder.append("§f§a§i§r§x§a§e§r§o §n§o§m§i§n§i§m§a§p");
                break;

            case "fairplay_nether":
                messageBuilder.append("§f§a§i§r§x§a§e§r§o §x§a§e§r§o§w§m§n§e§t§h§e§r§i§s§f§a§i§r");
                break;

            case "none":
            default:
                break;
        }

        if (!messageBuilder.isEmpty()) {
            getServer().getGlobalRegionScheduler().runDelayed(
                    this,
                    scheduledTask -> {
                        getServer().dispatchCommand(getServer().getConsoleSender(),
                                "tellraw " + player.getName() + " \"" + messageBuilder.toString() + "\"");
                    },
                    1L);
        }
    }
}