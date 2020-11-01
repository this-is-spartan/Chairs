package me.vomet.chairs.main;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    private static Main main;

    public static boolean isOccupied(Block block) {
        for (SitData data : sitting.values()) {
            if (data.chair.equals(block)) {
                return true;
            }
        }
        return false;
    }

    public static void sit(Player player, Block block) {
        if (sitting.containsKey(player.getUniqueId())) {
            if (sitting.get(player.getUniqueId()).chair.equals(block)) {
                return;
            }

            unsit(player);
        }
        sitting.put(player.getUniqueId(), new SitData(main, player, block));
    }

    public static void unsit(Player player) {
        sitting.get(player.getUniqueId()).unsit();
        sitting.remove(player.getUniqueId());
    }

    public static void unsit(Block chair) {
        UUID remove = null;
        for (UUID uuid : sitting.keySet()) {
            SitData data = sitting.get(uuid);
        if (data.chair.equals(chair)) {
            data.unsit();
        }
        remove = uuid;
        break;
    }
        if (remove != null) {
            sitting.remove(remove);
        }

}
    public static boolean isSitting(Player player) {
        return sitting.containsKey(player.getUniqueId());
    }

    public boolean isChair(Block block) {
        for (SitData data : sitting.values()) {
            if (data.chair.equals(block)) {
                return true;
            }
        }
        return false;
    }

    private static HashMap<UUID, SitData> sitting = new HashMap<>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Chairs plugin has started\nMade by ahmed.jar#0001\nContact on discord for support!");
        Bukkit.getPluginManager().registerEvents(new ChairListener(), this);
        Main.main = this;
    }


}
