package me.vomet.chairs.main;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.Objects;

public class ChairListener implements Listener {
    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && Objects.equals(e.getHand(), EquipmentSlot.HAND) && (e.getClickedBlock().getType().name().contains("STAIRS") || e.getClickedBlock().getType().name().contains("SLAB"))) {
            if (!Main.isOccupied(e.getClickedBlock())) {
            Main.sit(e.getPlayer(), e.getClickedBlock());
            }
        }


    }
    @EventHandler
    public void onEntityDismount(EntityDismountEvent e) {

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (Main.isSitting(player)) {
                Main.unsit(player);
            }
        }

    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        if (Main.isSitting(player)) {
            Main.unsit(player);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (Main.isSitting(player)) {
            Main.unsit(player);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (Main.isOccupied(e.getBlock())) {
            Main.unsit(e.getBlock());
        }
    }
}
