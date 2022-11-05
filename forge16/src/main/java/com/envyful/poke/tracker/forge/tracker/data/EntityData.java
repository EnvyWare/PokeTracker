package com.envyful.poke.tracker.forge.tracker.data;

import com.envyful.poke.tracker.forge.PokeTrackerForge;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Iterator;
import java.util.UUID;

public class EntityData {

    private final String pokemonName;
    private final String sprite;
    private final UUID entityUUID;
    private final long spawnTime;

    private boolean caught;
    private String catcher;

    public static EntityData of(PixelmonEntity pixelmon) {
        return new EntityData(pixelmon.getLocalizedName(), pixelmon.getSprite().toString(), pixelmon.getUUID(), System.currentTimeMillis(), false,
                              null
        );
    }

    public static EntityData of(PixelmonEntity pixelmon, long time, boolean caught) {
        return new EntityData(pixelmon.getLocalizedName(), pixelmon.getSprite().toString(), pixelmon.getUUID(), time, caught, null);
    }

    public static EntityData of(UUID uuid, String name, String sprite, long time, boolean caught, String catcher) {
        return new EntityData(name, sprite, uuid, time, caught, catcher);
    }

    private EntityData(String pokemonName, String sprite, UUID entityUUID, long spawnTime, boolean caught, String catcher) {
        this.pokemonName = pokemonName;
        this.sprite = sprite;
        this.entityUUID = entityUUID;
        this.spawnTime = spawnTime;
        this.caught = caught;
        this.catcher = catcher;
    }

    public String getPokemonName() {
        return this.pokemonName;
    }

    public String getSprite() {
        return this.sprite;
    }

    public UUID getEntityUUID() {
        return this.entityUUID;
    }

    public long getSpawnTime() {
        return this.spawnTime;
    }

    public Entity getEntity() {
        Iterator<ServerWorld> iterator = ServerLifecycleHooks.getCurrentServer().getAllLevels().iterator();

        while (iterator.hasNext()) {
            ServerWorld next = iterator.next();
            Entity entity = next.getEntity(this.getEntityUUID());

            if (entity != null) {
                return entity;
            }
        }

        return null;
    }

    public boolean isCaught() {
        return this.caught;
    }

    public void setCaught(boolean caught) {
        this.caught = caught;
    }

    public void setCatcher(ServerPlayerEntity catcher) {
        this.catcher = catcher.getName().getString();
    }

    public String getCatcher() {
        return this.getCatcher(false);
    }

    public String getCatcher(boolean forceDefault) {
        if (forceDefault) {
            return this.catcher;
        }

        if (!this.caught && this.catcher == null) {
            return PokeTrackerForge.getInstance().getLocale().getNoCatcher();
        }

        return PokeTrackerForge.getInstance().getLocale().getCaughtFormat().replace("%player%", this.catcher);
    }
}
