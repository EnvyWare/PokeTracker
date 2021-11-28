package com.envyful.poke.tracker.forge.tracker.listener;

import com.envyful.api.concurrency.UtilConcurrency;
import com.envyful.poke.tracker.forge.tracker.PokeTrackerFactory;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PokemonCatchListener {

    public PokemonCatchListener() {
        Pixelmon.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPixelmonSpawn(CaptureEvent.SuccessfulCapture event) {
        EntityPixelmon entity = event.getPokemon();

        UtilConcurrency.runAsync(() -> {
            PokeTrackerFactory.catchPokemon(entity, event.player);
        });
    }
}
