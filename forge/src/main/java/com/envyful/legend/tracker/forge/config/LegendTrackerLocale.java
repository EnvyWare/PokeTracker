package com.envyful.legend.tracker.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
@ConfigPath("config/LegendTracker/locale.yml")
public class LegendTrackerLocale extends AbstractYamlConfig {

    private String reloadedConfigs = "&e&l(!) &eReloaded configs";

    public LegendTrackerLocale() {
        super();
    }

    public String getReloadedConfigs() {
        return this.reloadedConfigs;
    }
}
