package com.envyful.poke.tracker.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.type.ConfigItem;
import com.envyful.api.config.type.ExtendedConfigItem;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pixelmonmod.api.pokemon.PokemonSpecification;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;
import java.util.Map;

@ConfigSerializable
@ConfigPath("config/PokeTracker/config.yml")
public class PokeTrackerConfig extends AbstractYamlConfig {

    private Map<String, TrackerSection> trackers = ImmutableMap.of(
            "legendaries", new TrackerSection(
                    "legends", "isLegend:1", Lists.newArrayList("hoopa"),
                    new ExtendedConfigItem("pixelmon:ui_element", 1, (byte) 0, "&bLast Legendaries",
                                               Lists.newArrayList(
                                                       "&8• &6%poketracker_pokemon_legends_1%&8 - &f%poketracker_time_legends_1% &8[&f%poketracker_status_legends_1%&8]",
                                                       "&8• &6%poketracker_pokemon_legends_2%&8 - &f%poketracker_time_legends_2% &8[&f%poketracker_status_legends_2%&8]",
                                                       "&8• &6%poketracker_pokemon_legends_3%&8 - &f%poketracker_time_legends_3% &8[&f%poketracker_status_legends_3%&8]"
                                               ), 1, 1,
                                               ImmutableMap.of(
                                                       "UIImage", new ConfigItem.NBTValue("string", "%sprite%")
                                               )
                    )
            ),
            "ultrabeast", new TrackerSection(
                    "ultrabeast", "isUB:1", Lists.newArrayList("hoopa"),
                    new ExtendedConfigItem("pixelmon:ui_element", 1, (byte) 0, "&bLast UltraBeasts",
                                               Lists.newArrayList(
                                                       "&8• &6%poketracker_pokemon_ultrabeast_1%&8 - &f%poketracker_time_ultrabeast_1% &8[&f%poketracker_status_ultrabeast_1%&8]",
                                                       "&8• &6%poketracker_pokemon_ultrabeast_2%&8 - &f%poketracker_time_ultrabeast_2% &8[&f%poketracker_status_ultrabeast_2%&8]",
                                                       "&8• &6%poketracker_pokemon_ultrabeast_3%&8 - &f%poketracker_time_ultrabeast_3% &8[&f%poketracker_status_ultrabeast_3%&8]"
                                               ), 3, 1,
                                               ImmutableMap.of(
                                                       "UIImage", new ConfigItem.NBTValue("string", "%sprite%")
                                               )
                    )
            )
    );

    private String caughtText = "Caught";
    private String defeatedText = "Defeated";
    private String despawnedText = "Despawned";
    private String activeText = "Active";

    public PokeTrackerConfig() {
        super();
    }

    public Map<String, TrackerSection> getTrackers() {
        return this.trackers;
    }

    public String getCaughtText() {
        return this.caughtText;
    }

    public String getDefeatedText() {
        return this.defeatedText;
    }

    public String getDespawnedText() {
        return this.despawnedText;
    }

    public String getActiveText() {
        return this.activeText;
    }

    @ConfigSerializable
    public static class TrackerSection {

        private String name;
        private String spec;
        private int maxStored = 3;
        private transient PokemonSpecification trackerSpec = null;
        private List<String> blacklist;
        private transient List<PokemonSpecification> blacklistSpec = null;
        private ExtendedConfigItem displayItem;

        public TrackerSection() {
        }

        public TrackerSection(String name, String spec, List<String> blacklist, ExtendedConfigItem displayItem) {
            this.name = name;
            this.spec = spec;
            this.blacklist = blacklist;
            this.displayItem = displayItem;
        }

        public String getName() {
            return this.name;
        }

        public int getMaxStored() {
            return this.maxStored;
        }

        public ExtendedConfigItem getDisplayItem() {
            return this.displayItem;
        }

        public PokemonSpecification getSpec() {
            if (this.trackerSpec == null) {
                this.trackerSpec = PokemonSpecificationProxy.create(this.spec).get();
            }

            return this.trackerSpec;
        }

        public List<PokemonSpecification> getBlacklist() {
            if (this.blacklistSpec == null) {
                List<PokemonSpecification> specs = Lists.newArrayList();

                for (String s : this.blacklist) {
                    specs.add(PokemonSpecificationProxy.create(s).get());
                }

                this.blacklistSpec = specs;
            }

            return this.blacklistSpec;
        }
    }
}
