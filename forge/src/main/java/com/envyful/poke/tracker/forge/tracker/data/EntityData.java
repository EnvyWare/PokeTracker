package com.envyful.poke.tracker.forge.tracker.data;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class EntityData {

    private final String pokemonName;
    private final UUID entityUUID;

    public static EntityData of(EntityPixelmon pixelmon) {
        return new EntityData(pixelmon.getPokemonName(), pixelmon.getUniqueID());
    }

    private EntityData(String pokemonName, UUID entityUUID) {
        this.pokemonName = pokemonName;
        this.entityUUID = entityUUID;
    }

    public String getPokemonName() {
        return this.pokemonName;
    }

    public UUID getEntityUUID() {
        return this.entityUUID;
    }

    public Entity getEntity() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityFromUuid(this.getEntityUUID());
    }
}
