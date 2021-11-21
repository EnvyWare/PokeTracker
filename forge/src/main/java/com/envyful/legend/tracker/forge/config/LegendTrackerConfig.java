package com.envyful.legend.tracker.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
@ConfigPath("config/LegendTracker/config.yml")
public class LegendTrackerConfig extends AbstractYamlConfig {

    private boolean messageNearestPlayer = true;
    private boolean broadcastSpawn = true;
    private int legendHistory = 3;
    private int shinyHistory = 3;
    private int ultraBeastHistory = 3;
    private int bossHistory = 3;

    private List<String> blacklist = Lists.newArrayList( "hoopa shiny:1");
    private transient List<PokemonSpec> blacklistedSpec = null;

    public LegendTrackerConfig() {
        super();
    }

    public boolean isMessageNearestPlayer() {
        return this.messageNearestPlayer;
    }

    public boolean isBroadcastSpawn() {
        return this.broadcastSpawn;
    }

    public List<PokemonSpec> getBlacklistedSpec() {
        if (this.blacklistedSpec == null) {
            List<PokemonSpec> specs = Lists.newArrayList();

            for (String s : this.blacklist) {
                specs.add(PokemonSpec.from(s));
            }

            this.blacklistedSpec = specs;
        }

        return this.blacklistedSpec;
    }

    public int getLegendHistory() {
        return this.legendHistory;
    }

    public int getShinyHistory() {
        return this.shinyHistory;
    }

    public int getUltraBeastHistory() {
        return this.ultraBeastHistory;
    }

    public int getBossHistory() {
        return this.bossHistory;
    }
}
