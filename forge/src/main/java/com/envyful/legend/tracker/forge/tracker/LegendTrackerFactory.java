package com.envyful.legend.tracker.forge.tracker;

import com.envyful.legend.tracker.forge.LegendTrackerForge;
import com.envyful.legend.tracker.forge.config.LegendTrackerConfig;
import com.envyful.legend.tracker.forge.tracker.data.EntityData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;

import java.util.List;
import java.util.Map;

public class LegendTrackerFactory {

    private static final Map<String, List<EntityData>> TRACKED_ENTITIES = Maps.newHashMap();

    public static List<EntityData> getTrackedEntities(String name) {
        return TRACKED_ENTITIES.get(name.toLowerCase());
    }

    public static void addTrackedEntities(EntityPixelmon pixelmon) {
        for (Map.Entry<String, LegendTrackerConfig.TrackerSection> entry : LegendTrackerForge.getInstance().getConfig().getTrackers().entrySet()) {
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
}
