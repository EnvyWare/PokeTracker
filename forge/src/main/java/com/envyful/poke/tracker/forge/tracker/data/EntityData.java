package com.envyful.poke.tracker.forge.tracker.data;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class EntityData {

    private final String pokemonName;
    private final UUID entityUUID;
    private final long spawnTime;

    private boolean caught;
    private String catcher;

    public static EntityData of(EntityPixelmon pixelmon) {
        return new EntityData(pixelmon.getPokemonName(), pixelmon.getUniqueID(), System.currentTimeMillis(), false,
                              null
        );
    }

    public static EntityData of(EntityPixelmon pixelmon, long time, boolean caught) {
        return new EntityData(pixelmon.getPokemonName(), pixelmon.getUniqueID(), time, caught, null);
    }

    public static EntityData of(UUID uuid, String name, long time, boolean caught, String catcher) {
        return new EntityData(name, uuid, time, caught, catcher);
    }

    private EntityData(String pokemonName, UUID entityUUID, long spawnTime, boolean caught, String catcher) {
        this.pokemonName = pokemonName;
        this.entityUUID = entityUUID;
        this.spawnTime = spawnTime;
        this.caught = caught;
        this.catcher = catcher;
    }

    public String getPokemonName() {
        return this.pokemonName;
    }

    public UUID getEntityUUID() {
        return this.entityUUID;
    }

    public long getSpawnTime() {
        return this.spawnTime;
    }

    public Entity getEntity() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityFromUuid(this.getEntityUUID());
    }

    public boolean isCaught() {
        return this.caught;
    }

    public void setCaught(boolean caught) {
        this.caught = caught;
    }

    public void setCatcher(EntityPlayerMP catcher) {
        this.catcher = catcher.getName();
    }

    public String getCatcher() {
        if (!this.caught || this.catcher == null) {
            return "None";
        }

        return this.catcher;
    }
}
