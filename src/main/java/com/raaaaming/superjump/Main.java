package com.raaaaming.superjump;

import java.io.File;
import java.util.HashMap;

import com.raaaaming.superjump.files.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public final class Main extends JavaPlugin implements Listener {
    public static String prefix = "&f&l [ &b슈퍼점프 &f&l] &7";

    public static HashMap<Location, Location> superjump = new HashMap<>();

    public void removePotion(Player p) {
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
    }

    public void onEnable() {
        CustomConfig.setup();
        CustomConfig.get().addDefault("distance", "10");
        CustomConfig.get().options().copyDefaults(true);
        CustomConfig.save();
        getLogger().info(prefix + "플러그인이 활성화되었습니다.");
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        getLogger().info(prefix + "플러그인이 비활성화되었습니다.");
    }

    @EventHandler
    public void onStep(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location loc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY()-1, p.getLocation().getZ());
        Block b = Bukkit.getWorld("world").getBlockAt(loc);
        if (e.getTo() != e.getFrom()) {
            if (b.getBlockData().getMaterial() == Material.GOLD_BLOCK) {
                p.sendMessage(String.valueOf(CustomConfig.get().getInt("distance")));
                p.setVelocity(p.getLocation().getDirection().multiply(CustomConfig.get().getInt("distance")));
            }
        }
    }
}
