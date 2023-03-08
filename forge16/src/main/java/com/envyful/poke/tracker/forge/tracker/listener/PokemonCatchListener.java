package com.envyful.poke.tracker.forge.tracker.listener;

import com.envyful.api.concurrency.UtilConcurrency;
import com.envyful.api.type.Pair;
import com.envyful.poke.tracker.forge.tracker.PokeTrackerFactory;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.battles.BattleResults;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Map;

public class PokemonCatchListener {

    public PokemonCatchListener() {
        Pixelmon.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPokemonCapture(CaptureEvent.SuccessfulCapture event) {
        PixelmonEntity entity = event.getPokemon();

        UtilConcurrency.runAsync(() -> {
            PokeTrackerFactory.catchPokemon(entity, event.getPlayer());
        });
    }

    @SubscribeEvent
    public void onBattleEnd(BattleEndEvent event) {
        Pair<ServerPlayerEntity, List<PixelmonEntity>> pair = this.wasWildDefeated(event);

        if (pair == null) {
            return;
        }

        UtilConcurrency.runAsync(() -> {
            for (PixelmonEntity entityPixelmon : pair.getY()) {
                PokeTrackerFactory.defeatPokemon(entityPixelmon, pair.getX());
            }
        });
    }

    private Pair<ServerPlayerEntity, List<PixelmonEntity>> wasWildDefeated(BattleEndEvent event) {
        List<PixelmonEntity> pixelmon = Lists.newArrayList();
        ServerPlayerEntity player = null;

        for (Map.Entry<BattleParticipant, BattleResults> entry : event.getResults().entrySet()) {
            if (entry.getKey() instanceof WildPixelmonParticipant) {
                for (PixelmonWrapper pixelmonWrapper : entry.getKey().allPokemon) {
                    pixelmon.add(pixelmonWrapper.entity);
                }
            } else if (entry.getKey() instanceof PlayerParticipant) {
                player = (ServerPlayerEntity) entry.getKey().getEntity();

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
