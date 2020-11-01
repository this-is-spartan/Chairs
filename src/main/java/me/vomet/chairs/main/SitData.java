package me.vomet.chairs.main;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class SitData {

    protected Player player;
    protected Block chair;

    private ArmorStand stand;
    private int task;

    public SitData(Main main, Player player, Block chair) {
        this.player = player;
        this.chair = chair;
        stand = (ArmorStand) chair.getLocation().getWorld().spawn(chair.getLocation().add(0.5D, 0.3D, 0.5D), ArmorStand.class, (settings) -> {
            settings.setGravity(false);
            settings.setMarker(true);
            settings.setSmall(true);
            settings.setVisible(false);
            settings.setCollidable(false);
            settings.setInvulnerable(true);
            settings.addPassenger(player);

        });

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                try {
                    Object obj = stand.getClass().getMethod("getHandle").invoke(stand);
                    obj.getClass().getField("yaw").set(obj, player.getLocation().getYaw());
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }, 4, 4);
    }

    public void unsit() {
        Bukkit.getScheduler().cancelTask(task);
        player.leaveVehicle();
        stand.remove();
    }
}
