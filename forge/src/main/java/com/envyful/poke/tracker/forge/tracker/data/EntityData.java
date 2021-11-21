package com.envyful.poke.tracker.forge.tracker.data;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class EntityData {

    private final String pokemonName;
    private final UUID entityUUID;
    private final long spawnTime;

    public static EntityData of(EntityPixelmon pixelmon) {
        return new EntityData(pixelmon.getPokemonName(), pixelmon.getUniqueID(), System.currentTimeMillis());
    }

    public static EntityData of(EntityPixelmon pixelmon, long time) {
        return new EntityData(pixelmon.getPokemonName(), pixelmon.getUniqueID(), time);
    }

    private EntityData(String pokemonName, UUID entityUUID, long spawnTime) {
        this.pokemonName = pokemonName;
        this.entityUUID = entityUUID;
        this.spawnTime = spawnTime;
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
}
