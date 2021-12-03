package com.envyful.poke.tracker.forge.tracker.listener;

import com.envyful.api.concurrency.UtilConcurrency;
import com.envyful.poke.tracker.forge.tracker.PokeTrackerFactory;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PokemonSpawnListener {

    public PokemonSpawnListener() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPixelmonSpawn(SpawnEvent event) {
        Entity entity = event.action.getOrCreateEntity();

        if (!(entity instanceof EntityPixelmon)) {
            return;
        }

        UtilConcurrency.runAsync(() -> {
            EntityPixelmon pixelmon = (EntityPixelmon) entity;

            if (pixelmon.getOwner() != null) {
                return;
            }

            PokeTrackerFactory.addTrackedEntities(pixelmon);
        });
    }
}
