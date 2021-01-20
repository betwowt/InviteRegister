package com.betwowt.inviteRegister.listener;

import io.izzel.taboolib.module.inject.TListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@TListener
public class ListenerBukkitInventory implements Listener {

    public static final Map<UUID, Player> lock = new ConcurrentHashMap<>();


    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (lock.get(event.getPlayer().getUniqueId()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            Player player = (Player) event.getWhoClicked();
            if (lock.get(player.getUniqueId()) != null) {
                event.setCancelled(true);
            }
        }
    }

}
