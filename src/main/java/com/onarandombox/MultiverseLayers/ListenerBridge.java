package com.onarandombox.MultiverseLayers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerBridge implements Listener {
    private final MultiverseLayers plugin;

    public ListenerBridge(MultiverseLayers plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent e) {
        plugin.onPlayerMove(e);
    }
}
