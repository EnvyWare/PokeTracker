package com.envyful.poke.tracker.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
@ConfigPath("config/PokeTracker/locale.yml")
public class PokeTrackerLocale extends AbstractYamlConfig {

    private String reloadedConfigs = "&e&l(!) &eReloaded configs";
    private String caughtFormat = "%player%";
    private String noCatcher = "";

    public PokeTrackerLocale() {
        super();
    }

    public String getReloadedConfigs() {
        return this.reloadedConfigs;
    }

    public String getCaughtFormat() {
        return this.caughtFormat;
    }

    public String getNoCatcher() {
        return this.noCatcher;
    }
}
