package com.envyful.legend.tracker.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.type.ConfigInterface;
import com.envyful.api.config.type.ConfigItem;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
@ConfigPath("config/LegendTracker/guis.yml")
public class LegendTrackerGui extends AbstractYamlConfig {

    private ConfigInterface guiSettings = new ConfigInterface(
            "Pokemon Tracker", 6, "BLOCK", ImmutableMap.of("one", new ConfigItem(
            "minecraft:stained_glass_pane", 1, (byte) 15, " ",
            Lists.newArrayList(), Maps.newHashMap()
    )));

    public LegendTrackerGui() {
        super();
    }

    public ConfigInterface getGuiSettings() {
        return this.guiSettings;
    }
}
