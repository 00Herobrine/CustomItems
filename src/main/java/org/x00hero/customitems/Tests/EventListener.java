package org.x00hero.customitems.Tests;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.x00hero.customitems.Custom.Events.EnteredCustomRegion;
import org.x00hero.customitems.Custom.Events.LeftCustomRegion;
import org.x00hero.customitems.Custom.Events.SustainedCustomRegion;

import java.time.Duration;

public class EventListener implements Listener {
    @EventHandler
    public void onEnter(EnteredCustomRegion e) {
        Player player = e.getPlayer();
        player.sendMessage("Entered region " + e.getRegion().ID);
    }
    @EventHandler
    public void onLeave(LeftCustomRegion e) {
        Player player = e.getPlayer();
        player.sendMessage("Left region " + e.getRegion().ID);
    }
    @EventHandler
    public void onSustain(SustainedCustomRegion e) {
        Player player = e.getPlayer();
        Duration duration = Duration.ofMillis(e.getDuration());
        StringBuilder sb = new StringBuilder();
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        if(hours > 0) sb.append(hours + " hour(s) ");
        if(minutes > 0) sb.append(minutes + " min(s) ");
        if(seconds > 0 || sb.isEmpty()) sb.append(seconds + " second(s)");
        sb.append(".");
        //String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        player.sendMessage("Sustained region " + e.getRegion().ID + " for " + sb);
    }
}
