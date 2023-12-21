package org.x00hero.Stamina;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

import static org.x00hero.Main.plugin;

public class StaminaContainer {
    // temp
    private UUID holder;
    private long nextDrain = 0;
    private long nextRegen = 0;
    private long regenCooldown = 0;
    // Config
    private double amount;
    private double max;
    private double regenInterval;
    private double drainInterval;
    private boolean exhausted;
    public StaminaSection Idle;
    public StaminaSection Walk;
    public StaminaSection Jump;
    public StaminaSection Sprint;
    public StaminaSection Exhausted;
    public StaminaContainer(StaminaContainer container) {
        holder = container.holder;
        nextDrain = container.nextDrain;
        nextRegen = container.nextRegen;
        regenCooldown = container.regenCooldown;
        amount = container.amount;
        max = container.max;
        regenInterval = container.regenInterval;
        drainInterval = container.drainInterval;
        exhausted = container.exhausted;
        Idle = container.Idle;
        Walk = container.Walk;
        Jump = container.Jump;
        Sprint = container.Sprint;
        Exhausted = container.Exhausted;
    }
    public StaminaContainer(Player player, ConfigurationSection section) {
        holder = player.getUniqueId();
        amount = section.getDouble("Stamina.amount");
        max = section.getDouble("Stamina.max");
        regenInterval = section.getDouble("Stamina.regen-interval");
        drainInterval = section.getDouble("Stamina.drain-interval");
        exhausted = section.getBoolean("Stamina.exhausted");
        Idle = new StaminaSection(Objects.requireNonNull(section.getConfigurationSection("Idle")));
        Walk = new StaminaSection(Objects.requireNonNull(section.getConfigurationSection("Walk")));
        Jump = new StaminaSection(Objects.requireNonNull(section.getConfigurationSection("Jump")));
        Sprint = new StaminaSection(Objects.requireNonNull(section.getConfigurationSection("Sprint")));
        Exhausted = new StaminaSection(Objects.requireNonNull(section.getConfigurationSection("Exhausted")));
    }

    public double getIdleRegenDelay() { return Idle.regenDelay; }
    public double getWalkRegenDelay() { return Walk.regenDelay; }
    public double getJumpRegenDelay() { return Jump.regenDelay; }
    public double getSprintRegenDelay() { return Sprint.regenDelay; }
    public double getExhaustedRegenDelay() { return Exhausted.regenDelay; }
    public double getRegenDelay(DrainCause cause) {
        switch(cause) {
            default -> { return Idle.regenDelay; }
            case WALK -> { return Walk.regenDelay; }
            case JUMP -> { return Jump.regenDelay; }
            case SPRINT -> { return Sprint.regenDelay; }
            case EXHAUSTED -> { return Exhausted.regenDelay; }
        }
    }

    public double getIdleRegenAmount() { return Idle.regenAmount; }
    public double getWalkRegenAmount() { return Walk.regenAmount; }
    public double getSprintRegenAmount() { return Sprint.regenAmount; }
    public double getJumpRegenAmount() { return Jump.regenAmount; }
    public double getExhaustedRegenMod() { return Exhausted.regenAmount; }
    public double getRegenAmount(DrainCause type) {
        switch(type) {
            default -> { return Idle.regenAmount; }
            case WALK -> { return Walk.regenAmount; }
            case JUMP -> { return Jump.regenAmount; }
            case SPRINT -> { return Sprint.regenAmount; }
            case EXHAUSTED -> { return Exhausted.regenAmount; }
        }
    }

    public double getIdleDrainAmount() { return Idle.drainAmount; }
    public double getWalkDrainAmount() { return Walk.drainAmount; }
    public double getJumpDrainAmount() { return Jump.drainAmount; }
    public double getSprintDrainAmount() { return Sprint.drainAmount; }
    public double getExhaustedDrainMod() { return Exhausted.drainAmount; }
    public double getDrainAmount(DrainCause type) {
        switch(type) {
            default -> { return Idle.drainAmount; }
            case WALK -> { return Walk.drainAmount; }
            case JUMP -> { return Jump.drainAmount; }
            case SPRINT -> { return Sprint.drainAmount; }
            case EXHAUSTED -> { return Exhausted.drainAmount; }
        }
    }

    public void setIdleRegenDelay(double regenDelay) { setRegenCooldown(regenDelay, DrainCause.IDLE); }
    public void setWalkRegenDelay(double regenDelay) { setRegenCooldown(regenDelay, DrainCause.WALK); }
    public void setSprintRegenDelay(double regenDelay) { setRegenCooldown(regenDelay, DrainCause.SPRINT); }
    public void setJumpRegenDelay(double regenDelay) { setRegenCooldown(regenDelay, DrainCause.JUMP); }
    public void setExhaustedRegenDelay(double regenDelay) { setRegenCooldown(regenDelay, DrainCause.EXHAUSTED); }
    public void setRegenCooldown(double delay, DrainCause type) {
        switch(type) {
            case IDLE -> Idle.regenDelay = delay;
            case WALK -> Walk.regenDelay = delay;
            case JUMP -> Jump.regenDelay = delay;
            case SPRINT -> Sprint.regenDelay = delay;
            case EXHAUSTED -> Exhausted.regenDelay = delay;
        }
    }

    public void setIdleRegenAmount(double regenAmount) { setRegenAmount(regenAmount, DrainCause.IDLE); }
    public void setWalkRegenAmount(double regenAmount) { setRegenAmount(regenAmount, DrainCause.WALK); }
    public void setSprintRegenAmount(double regenAmount) { setRegenAmount(regenAmount, DrainCause.SPRINT); }
    public void setJumpRegenAmount(double regenAmount) { setRegenAmount(regenAmount, DrainCause.JUMP); }
    public void setExhaustedRegenMod(double regenAmount) { setRegenAmount(regenAmount, DrainCause.EXHAUSTED); }
    public void setRegenAmount(double amount, DrainCause type) {
        switch(type) {
            case IDLE -> Idle.regenAmount = amount;
            case WALK -> Walk.regenAmount = amount;
            case JUMP -> Jump.regenAmount = amount;
            case SPRINT -> Sprint.regenAmount = amount;
            case EXHAUSTED -> Exhausted.regenAmount = amount;
        }
    }

    public void setIdleDrainAmount(double idleDrainAmount) { setDrainAmount(idleDrainAmount, DrainCause.IDLE); }
    public void setWalkDrainAmount(double walkDrainAmount) { setDrainAmount(walkDrainAmount, DrainCause.SPRINT); }
    public void setSprintDrainAmount(double sprintDrainAmount) { setDrainAmount(sprintDrainAmount, DrainCause.SPRINT); }
    public void setJumpDrainAmount(double jumpDrainAmount) { setDrainAmount(jumpDrainAmount, DrainCause.JUMP); }
    public void setExhaustedDrainMod(double exhaustedDrainMod) { setDrainAmount(exhaustedDrainMod, DrainCause.EXHAUSTED); }
    public void setDrainAmount(double amount, DrainCause type) {
        switch(type) {
            case IDLE -> Idle.drainAmount = amount;
            case WALK -> Walk.drainAmount = amount;
            case JUMP -> Jump.drainAmount = amount;
            case SPRINT -> Sprint.drainAmount = amount;
            case EXHAUSTED -> Exhausted.drainAmount = amount;
        }
    }
    public StaminaContainer drain(double amount) { return drain(amount, drainInterval); }
    public StaminaContainer drain(double amount, double interval) { this.amount -= amount; if(interval > 0) setNextDrain(interval); return this; }
    public StaminaContainer regen(double amount) { setAmount(this.amount + amount); setNextRegen(regenInterval); return this; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = Math.min(amount, max); }
    public boolean hasAmount(double amount) { return this.amount >= amount; }
    public float getPercentage() { return (float) Math.min(amount / max, 1); }
    public double getMaxAmount() { return max; }
    public void setMaxAmount(double maxAmount) { this.max = maxAmount; }

    public boolean isExhausted() { return (exhausted || amount == 0.0D); }
    public void setExhausted(boolean exhausted) { this.exhausted = exhausted; }

    public UUID getHolderID() { return holder; }
    public Player getHolder() { return Bukkit.getPlayer(holder); }
    public void cancelSprint() {
        Player player = getHolder();
        int original = player.getFoodLevel();
        player.setFoodLevel(2);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.setFoodLevel(original), 10L);
    }

    public double getRegenInterval() { return regenInterval; }
    public boolean RegenDelayed() { return nextRegen <= System.currentTimeMillis(); }
    public long getRegenCooldown() { return regenCooldown; }
    public void setRegenCooldown(double delay) { // The delay where you can't drain stamina in order to regen.
        long newTime = (long) (System.currentTimeMillis() + (delay * 1000));
        if(getRegenCooldown() > newTime) return;
        regenCooldown = newTime;
    }
    public long getNextRegen() { return nextRegen; }
    public void setNextRegen(long nextRegen) { this.nextRegen = nextRegen; }
    public void setNextRegen(double delay) { setNextRegen((long) (System.currentTimeMillis() + (delay * 1000))); }
    public long getTimeUntilRegen() { long remaining; return (remaining = regenCooldown - System.currentTimeMillis()) > 0 ? remaining : 0; }

    public double getDrainInterval() { return drainInterval; }
    public boolean DrainDelayed() { return nextDrain <= System.currentTimeMillis(); }
    public long getNextDrain() { return nextDrain; }
    public void setNextDrain(double delay) { setNextDrain((long) (System.currentTimeMillis() + (delay * 1000L))); }
    public void setNextDrain(long nextDrain) { this.nextDrain = nextDrain; }
    public long getTimeUntilDelay() { long remaining; return (remaining = nextDrain - System.currentTimeMillis()) > 0 ? remaining : 0; }
    public void UpdateStaminaBar() { StaminaController.UpdateStaminaBar(getHolder()); }
    @Override
    public String toString() {
        return  "Stamina:{" +
                "holder:" + holder + "," +
                "nextDrain:" + nextDrain + "," +
                "nextRegen:" + nextRegen + "," +
                "regenCooldown:" + regenCooldown + "," +
                "amount:" + amount + "," +
                "regenInterval:" + regenInterval + "," +
                "drainInterval:" + drainInterval + "," +
                "maxAmount:" + max + "," +
                "exhausted:" + exhausted + "," +
                "Sections:{" + "idle" + Idle + "," +
                "walk" + Walk + "," +
                "jump" + Jump + "," +
                "sprint" + Sprint + "," +
                "exhausted" + Exhausted +
                "}}";
    }

    public static class StaminaSection {
        public double regenDelay;
        public double regenAmount;
        public double drainAmount;

        public StaminaSection(ConfigurationSection section) {
            regenDelay = section.getDouble("regen-delay");
            regenAmount = section.getDouble("regen-amount");
            drainAmount = section.getDouble("drain-amount");
        }

        @Override
        public String toString() {
            return  "Section:{regenDelay:" + regenDelay + "," +
                    "regenAmount:" + regenAmount + "," +
                    "drainAmount:" + drainAmount + "}";
        }
    }
}
