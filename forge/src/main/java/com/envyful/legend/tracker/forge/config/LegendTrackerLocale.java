package com.envyful.legend.tracker.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
@ConfigPath("config/LegendTracker/locale.yml")
public class LegendTrackerLocale extends AbstractYamlConfig {

    public LegendTrackerLocale() {
        super();
    }

}
