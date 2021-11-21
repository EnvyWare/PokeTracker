package com.envyful.poke.tracker.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
@ConfigPath("config/PokeTracker/locale.yml")
public class PokeTrackerLocale extends AbstractYamlConfig {

    private String reloadedConfigs = "&e&l(!) &eReloaded configs";

    public PokeTrackerLocale() {
        super();
    }

    public String getReloadedConfigs() {
        return this.reloadedConfigs;
    }
}
