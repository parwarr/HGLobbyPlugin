package de.parwar.lobbyplugin;

import de.parwar.lobbyplugin.commands.Menu;
import de.parwar.lobbyplugin.handlers.PlayerHandler;
import de.parwar.lobbyplugin.listeners.BreakBlockListener;
import de.parwar.lobbyplugin.listeners.PlaceBockListener;
import de.parwar.lobbyplugin.listeners.PlayerDamage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Server is on!!!!!!!!!!!!s");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getCommand("menu").setExecutor(new Menu(this));

        new BreakBlockListener(this);
        new PlaceBockListener(this);
        new PlayerHandler(this);
        new PlayerDamage(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
