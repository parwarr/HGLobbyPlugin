package de.parwar.lobbyplugin.listeners;

import de.parwar.lobbyplugin.LobbyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceBockListener implements Listener {

    public PlaceBockListener(LobbyPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    private void  onBlockPlace(BlockPlaceEvent event) {
            event.setCancelled(true);
    }
}
