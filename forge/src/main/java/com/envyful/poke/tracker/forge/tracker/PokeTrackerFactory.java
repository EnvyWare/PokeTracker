package com.envyful.poke.tracker.forge.tracker;

import com.envyful.poke.tracker.forge.PokeTrackerForge;
import com.envyful.poke.tracker.forge.config.PokeTrackerConfig;
import com.envyful.poke.tracker.forge.tracker.data.EntityData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class PokeTrackerFactory {

    private static final Map<String, List<EntityData>> TRACKED_ENTITIES = Maps.newHashMap();
    private static final Path LEGEND_TRACKER_FILE = Paths.get("config/WonderTradeForge/pool.json");

    public static List<EntityData> getTrackedEntities(String name) {
        return TRACKED_ENTITIES.get(name.toLowerCase());
    }

    public static void addTrackedEntities(EntityPixelmon pixelmon) {
        for (Map.Entry<String, PokeTrackerConfig.TrackerSection> entry : PokeTrackerForge.getInstance().getConfig().getTrackers().entrySet()) {
            if (!entry.getValue().getSpec().matches(pixelmon)) {
                continue;
            }

            List<EntityData> entities = TRACKED_ENTITIES.computeIfAbsent(entry.getKey().toLowerCase(), ___ -> Lists.newArrayList());

            entities.add(0, EntityData.of(pixelmon));

            if (entities.size() > entry.getValue().getMaxStored()) {
                entities.remove(entities.size() - 1);
            }
        }
    }

    public static void load() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            for (PokemonSpec pokemonSpec : tradePool) {
                ByteBuf buf = Unpooled.buffer();
                pokemonSpec.toBytes(buf);
                bufferedWriter.write(buf.toString(StandardCharsets.UTF_8));
                bufferedWriter.newLine();
                buf.release();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {

    }
}
