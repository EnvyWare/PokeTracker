package com.envyful.poke.tracker.forge.tracker.listener;

import com.envyful.api.concurrency.UtilConcurrency;
import com.envyful.api.type.Pair;
import com.envyful.poke.tracker.forge.tracker.PokeTrackerFactory;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Map;

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

    @SubscribeEvent
    public void onPixelmonSpawn(BattleEndEvent event) {
        Pair<EntityPlayerMP, List<EntityPixelmon>> pair = this.wasWildDefeated(event);

        if (pair == null) {
            return;
        }

        UtilConcurrency.runAsync(() -> {
            for (EntityPixelmon entityPixelmon : pair.getY()) {
                PokeTrackerFactory.defeatPokemon(entityPixelmon, pair.getX());
            }
        });
    }

    private Pair<EntityPlayerMP, List<EntityPixelmon>> wasWildDefeated(BattleEndEvent event) {
        List<EntityPixelmon> pixelmon = Lists.newArrayList();
        EntityPlayerMP player = null;

        for (Map.Entry<BattleParticipant, BattleResults> entry : event.results.entrySet()) {
            if (entry.getKey() instanceof WildPixelmonParticipant) {
                for (PixelmonWrapper pixelmonWrapper : entry.getKey().allPokemon) {
                    pixelmon.add(pixelmonWrapper.entity);
                }
            } else if (entry.getKey() instanceof PlayerParticipant) {
                player = (EntityPlayerMP) entry.getKey().getEntity();

                if (entry.getValue() != BattleResults.VICTORY) {
                    return null;
                }
            }
        }

        if (player == null || pixelmon.isEmpty()) {
            return null;
        }

        return Pair.of(player, pixelmon);
    }
}
