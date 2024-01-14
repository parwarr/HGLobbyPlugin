package de.parwar.lobbyplugin.commands;

import de.parwar.lobbyplugin.LobbyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import java.util.ArrayList;
import java.util.List;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import static net.md_5.bungee.api.ChatMessageType.ACTION_BAR;

public class Menu implements Listener, CommandExecutor {
    private final String invName = "Server Selector";
    private final LobbyPlugin plugin;
    public Menu(LobbyPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(invName)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        ItemMeta itemMeta = clickedItem.getItemMeta();

        if (itemMeta == null || !itemMeta.hasDisplayName()) {
            return;
        }

        String itemName = ChatColor.stripColor(itemMeta.getDisplayName());

        if (itemName.equalsIgnoreCase("HG")) {
            // Connect player to the HG server
            sendPlayerToServer(player, "HG");
        } else if (itemName.equalsIgnoreCase("Spawn")) {
            // Execute the /spawn command
            Bukkit.dispatchCommand(player, "spawn");
        }
        event.setCancelled(true);
    }

    private void sendPlayerToServer(Player player, String targetServer) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(targetServer);

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return  true;
        }

        Player player = (Player) sender;

        Inventory inv = Bukkit.createInventory(player, 9 * 3, invName);

        inv.setItem(13, getItem(new ItemStack(Material.MUSHROOM_SOUP),"&9HG", "&aClick to join", "&aPlay some OG HG"));
        inv.setItem(11, getItem(new ItemStack(Material.REDSTONE), "&8Spawn", "&aClick to Spawn", "Get back to the spawn"));

        player.openInventory(inv);

        return true;
    }


    private ItemStack getItem(ItemStack item, String name, String ... lore){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        List<String> lores = new ArrayList<>();
        for (String s : lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(lores);

        item.setItemMeta(meta);
        return item;
    }
}