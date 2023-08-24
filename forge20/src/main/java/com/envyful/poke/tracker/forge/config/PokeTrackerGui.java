package com.envyful.poke.tracker.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.type.ConfigInterface;
import com.envyful.api.config.type.ConfigItem;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.google.common.collect.ImmutableMap;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
@ConfigPath("config/PokeTracker/guis.yml")
public class PokeTrackerGui extends AbstractYamlConfig {

    private ConfigInterface guiSettings = new ConfigInterface(
            "Pokemon Tracker", 6, "BLOCK", ImmutableMap.of("one", ConfigItem.builder()
                    .type("minecraft:black_stained_glass_pane")
                    .amount(1)
                    .name(" ")
                    .build()
    ));

    public PokeTrackerGui() {
        super();
    }

    public ConfigInterface getGuiSettings() {
        return this.guiSettings;
    }
}
