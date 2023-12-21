package org.x00hero.Stamina.Events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffectType;
import org.x00hero.Stamina.DrainCause;
import org.x00hero.Stamina.Events.Custom.PlayerFallEvent;
import org.x00hero.Stamina.Events.Custom.PlayerJumpEvent;
import org.x00hero.Stamina.StaminaContainer;
import org.x00hero.Stamina.StaminaController;

import java.util.HashMap;
import java.util.UUID;

import static org.x00hero.customitems.Main.*;

public class PlayerMovement implements Listener {
    private HashMap<UUID, Location> falling = new HashMap<>();
    private static int airThreshold = 2;
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        StaminaController.RegisterPlayer(e.getPlayer());
    }
    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        StaminaController.unregister(e.getPlayer());
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        double yVelocity = player.getVelocity().getY();
        //Log("Moving " +  player.getVelocity());
        Location location = player.getLocation();
        if(yVelocity < -0.078 && yVelocity > -0.079) {
            if(falling.containsKey(uuid) && !location.getBlock().getRelative(BlockFace.DOWN).getType().isAir()) {
                Bukkit.getServer().getPluginManager().callEvent(new PlayerFallEvent(player, falling.remove(uuid), e.getTo()));
            }
        } else if(yVelocity < 0) {
            Block check = e.getTo().getBlock().getRelative(BlockFace.DOWN);
            for(int i = 0; i < airThreshold; i++) { // Log("Checking " + check.getType() + " @ " + check.getLocation().getX() + ", " + check.getLocation().getY() + ", " + check.getLocation().getZ());
                if(!check.getType().isAir()) return;
                check = check.getRelative(BlockFace.DOWN);
            }
            if(e.getTo().getY() <= e.getFrom().getY() && !falling.containsKey(uuid))
                falling.put(uuid, e.getFrom());
        } else if(yVelocity > 0 && !player.hasPotionEffect(PotionEffectType.LEVITATION)) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if(location.clone().add(0, 1, 0).getY() <= player.getLocation().getY()) {
                    if(!player.isFlying()) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerJumpEvent(player, e.getFrom(), player.getLocation()));
                        Block check = e.getTo().getBlock().getRelative(BlockFace.DOWN);
                        for(int i = 0; i < airThreshold; i++) {
                            if(!check.getType().isAir()) return;
                            check = check.getRelative(BlockFace.DOWN);
                        }
                        if(e.getTo().getY() <= e.getFrom().getY() && !falling.containsKey(uuid))
                            falling.put(uuid, location);
                    }
                }
            }, 3);
        }
        StaminaController.StaminaCheck(player, e);
    }
    @EventHandler
    public void onSprint(PlayerToggleSprintEvent e) {
        Player player = e.getPlayer();
        StaminaContainer container = StaminaController.getStaminaContainer(player);
        if(e.isSprinting() && container.isExhausted()) {
            Log("Cancelling Sprint");
            float original = player.getWalkSpeed();
            player.setWalkSpeed(0.0f);
            e.setCancelled(true);
            player.setWalkSpeed(original);
        }
    }
    @EventHandler
    public void onJump(PlayerJumpEvent e) {
        Player player = e.getPlayer();
        GameMode gameMode = player.getGameMode();
        if(gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR) return;
        if(!StaminaController.drainStamina(player, 0, DrainCause.JUMP)) e.setCancelled(true);
    }

}
