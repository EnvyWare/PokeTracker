package com.envyful.poke.tracker.forge.tracker.listener;

import com.envyful.api.concurrency.UtilConcurrency;
import com.envyful.poke.tracker.forge.tracker.PokeTrackerFactory;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PokemonSpawnListener {

    public PokemonSpawnListener() {
        Pixelmon.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPixelmonSpawn(SpawnEvent event) {
        Entity entity = event.action.getOrCreateEntity();

        if (!(entity instanceof PixelmonEntity)) {
            return;
        }

        UtilConcurrency.runAsync(() -> {
            PixelmonEntity pixelmon = (PixelmonEntity) entity;

            if (pixelmon.getOwner() != null) {
                return;
            }

            PokeTrackerFactory.addTrackedEntities(pixelmon);
        });
    }
}
