package de.parwar.lobbyplugin.handlers;

import de.parwar.lobbyplugin.LobbyPlugin;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHandler implements Listener {
    public PlayerHandler(LobbyPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        player.getInventory().clear();
        player.setBedSpawnLocation(new Location(player.getWorld(), -196.700, 72.00000, -330.002));
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        ItemStack lobby = new ItemStack(Material.NETHER_STAR, 1);
        ItemStack chest = new ItemStack(Material.CHEST, 1);
        Inventory inv = player.getInventory();

        ItemMeta metaCompass = compass.getItemMeta();
        if (metaCompass != null) {
            metaCompass.setDisplayName(ChatColor.RED + "Selector");
            compass.setItemMeta(metaCompass);

            // Add a custom enchantment to the compass item
            compass.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        }

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwner(player.getName()); // Set the owner of the skull
            skullMeta.setDisplayName(ChatColor.GREEN + name);
            head.setItemMeta(skullMeta);
        }

        ItemMeta metaLobby = lobby.getItemMeta();
        if (metaLobby != null) {
            metaLobby.setDisplayName(ChatColor.DARK_PURPLE + "Lobby");
            lobby.setItemMeta(metaLobby);
        }

        inv.setItem(0, compass);
        inv.setItem(8, head);
        inv.setItem(4, lobby);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = event.getItem();

        if (heldItem == null || heldItem.getType() != Material.COMPASS) {
            return; // Not a compass or null item
        }

        // Check for the custom enchantment
        if (heldItem.containsEnchantment(Enchantment.DURABILITY)) {
            // The player right-clicked the custom compass
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Bukkit.getServer().dispatchCommand(player, "menu");
            }
        }
    }
}
