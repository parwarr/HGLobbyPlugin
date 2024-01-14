package de.parwar.lobbyplugin.listeners;

import de.parwar.lobbyplugin.LobbyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlockListener implements Listener {
    public BreakBlockListener(LobbyPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
      event.setCancelled(true);
    }
}
