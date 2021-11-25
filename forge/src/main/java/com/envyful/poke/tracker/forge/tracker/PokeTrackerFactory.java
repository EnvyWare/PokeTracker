package com.envyful.poke.tracker.forge.tracker;

import com.envyful.poke.tracker.forge.PokeTrackerForge;
import com.envyful.poke.tracker.forge.config.PokeTrackerConfig;
import com.envyful.poke.tracker.forge.tracker.data.EntityData;
import com.envyful.poke.tracker.forge.tracker.data.EntityDataTypeAdapter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PokeTrackerFactory {

    private static final Map<String, List<EntityData>> TRACKED_ENTITIES = Maps.newHashMap();
    private static final Path POKE_TRACKER_FILE = Paths.get("config/PokeTracker/tracker.json");
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(EntityData.class, new EntityDataTypeAdapter())
            .create();
    private static final Type TYPE = TypeToken.get(TRACKED_ENTITIES.getClass()).getType();

    public static List<EntityData> getTrackedEntities(String name) {
        return TRACKED_ENTITIES.getOrDefault(name.toLowerCase(), Collections.emptyList());
    }

    public static void addTrackedEntities(EntityPixelmon pixelmon) {
        for (Map.Entry<String, PokeTrackerConfig.TrackerSection> entry : PokeTrackerForge.getInstance().getConfig().getTrackers().entrySet()) {
            if (!entry.getValue().getSpec().matches(pixelmon)) {
                continue;
            }

            List<EntityData> entities = TRACKED_ENTITIES.computeIfAbsent(entry.getValue().getName().toLowerCase(), ___ -> Lists.newArrayList());

            entities.add(0, EntityData.of(pixelmon));

            if (entities.size() > entry.getValue().getMaxStored()) {
                entities.remove(entities.size() - 1);
            }
        }
    }

    public static void catchPokemon(EntityPixelmon pixelmon) {
        for (List<EntityData> value : TRACKED_ENTITIES.values()) {
            for (EntityData entityData : value) {
                if (entityData.isCaught()) {
                    continue;
                }

                if (entityData.getEntityUUID() == pixelmon.getUniqueID()) {
                    entityData.setCaught(true);
                }
            }
        }
    }

    public static void load() {
        try {
            if (!POKE_TRACKER_FILE.toFile().exists()) {
                POKE_TRACKER_FILE.toFile().createNewFile();
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(POKE_TRACKER_FILE.toFile()));
            Map<String, List<LinkedTreeMap<String, Object>>> trackedEntities = GSON.fromJson(bufferedReader, Map.class);

            if (trackedEntities == null) {
                return;
            }

            for (Map.Entry<String, List<LinkedTreeMap<String, Object>>> entry : trackedEntities.entrySet()) {
                for (LinkedTreeMap<String, Object> treeEntry : entry.getValue()) {
                    TRACKED_ENTITIES.computeIfAbsent(entry.getKey(), ___ -> Lists.newArrayList()).add(
                            EntityData.of(
                                    UUID.fromString((String) treeEntry.get("entityUUID")),
                                    (String) treeEntry.get("pokemonName"),
                                    (long) ((double) treeEntry.get("spawnTime")),
                                    (boolean) treeEntry.get("caught"),
                                    (String) treeEntry.get("catcher")
                            )
                    );
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            if (!POKE_TRACKER_FILE.toFile().exists()) {
                POKE_TRACKER_FILE.toFile().createNewFile();
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(POKE_TRACKER_FILE.toFile()));
            GSON.toJson(TRACKED_ENTITIES, bufferedWriter);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
