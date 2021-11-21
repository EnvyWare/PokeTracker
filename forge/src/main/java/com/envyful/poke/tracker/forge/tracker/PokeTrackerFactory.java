package com.envyful.poke.tracker.forge.tracker;

import com.envyful.api.json.UtilGson;
import com.envyful.poke.tracker.forge.PokeTrackerForge;
import com.envyful.poke.tracker.forge.config.PokeTrackerConfig;
import com.envyful.poke.tracker.forge.tracker.data.EntityData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokeTrackerFactory {

    private static final Map<String, List<EntityData>> TRACKED_ENTITIES = Maps.newHashMap();
    private static final Path POKE_TRACKER_FILE = Paths.get("config/PokeTracker/tracker.json");

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
            if (!POKE_TRACKER_FILE.toFile().exists()) {
                POKE_TRACKER_FILE.toFile().createNewFile();
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(POKE_TRACKER_FILE.toFile()));
            TRACKED_ENTITIES.putAll(UtilGson.GSON.fromJson(bufferedReader, HashMap.class));
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
            UtilGson.GSON.toJson(TRACKED_ENTITIES, bufferedWriter);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
