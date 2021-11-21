package com.envyful.poke.tracker.forge.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.type.ConfigItem;
import com.envyful.api.config.type.PositionableConfigItem;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;
import java.util.Map;

@ConfigSerializable
@ConfigPath("config/PokeTracker/config.yml")
public class PokeTrackerConfig extends AbstractYamlConfig {

    private Map<String, TrackerSection> trackers = ImmutableMap.of(
            "legends", new TrackerSection(
                    "legendaries", "isLegend:1", Lists.newArrayList("hoopa"),
                    new PositionableConfigItem("pixelmon:ui_element", 1, (byte) 0, "&bLast Legendaries",
                                               Lists.newArrayList(
                                                       "&8• &6%pokemon_legends_1%&8 - &f%time% &8[&f%status_legends_1%&8]",
                                                       "&8• &6%pokemon_legends_2%&8 - &f%time% &8[&f%status_legends_2%&8]",
                                                       "&8• &6%pokemon_legends_3%&8 - &f%time% &8[&f%status_legends_3%&8]"
                                               ), 1, 1,
                                               ImmutableMap.of(
                                                       "UIImage", new ConfigItem.NBTValue("string", "texture:pixelmon/textures/gui/uielements/flatcat.png")
                                               )
                    )
            ),
            "ultrabeast", new TrackerSection(
                    "ultrabeast", "isUB:1", Lists.newArrayList("hoopa"),
                    new PositionableConfigItem("pixelmon:ui_element", 1, (byte) 0, "&bLast UltraBeasts",
                                               Lists.newArrayList(
                                                       "&8• &6%pokemon_ultrabeast_1%&8 - &f%time% &8[&f%status_ultrabeast_1%&8]",
                                                       "&8• &6%pokemon_ultrabeast_2%&8 - &f%time% &8[&f%status_ultrabeast_2%&8]",
                                                       "&8• &6%pokemon_ultrabeast_3%&8 - &f%time% &8[&f%status_ultrabeast_3%&8]"
                                               ), 3, 1,
                                               ImmutableMap.of(
                                                       "UIImage", new ConfigItem.NBTValue("string", "texture:pixelmon/textures/gui/uielements/flatcat.png")
                                               )
                    )
            )
    );

    public PokeTrackerConfig() {
        super();
    }

    public Map<String, TrackerSection> getTrackers() {
        return this.trackers;
    }

    @ConfigSerializable
    public static class TrackerSection {

        private String name;
        private String spec;
        private int maxStored = 3;
        private transient PokemonSpec trackerSpec = null;
        private List<String> blacklist;
        private transient List<PokemonSpec> blacklistSpec = null;
        private PositionableConfigItem displayItem;

        public TrackerSection(String name, String spec, List<String> blacklist, PositionableConfigItem displayItem) {
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

        public PositionableConfigItem getDisplayItem() {
            return this.displayItem;
        }

        public PokemonSpec getSpec() {
            if (this.trackerSpec == null) {
                this.trackerSpec = PokemonSpec.from(this.spec);
            }

            return this.trackerSpec;
        }

        public List<PokemonSpec> getBlacklist() {
            if (this.blacklistSpec == null) {
                List<PokemonSpec> specs = Lists.newArrayList();

                for (String s : this.blacklist) {
                    specs.add(PokemonSpec.from(s));
                }

                this.blacklistSpec = specs;
            }

            return this.blacklistSpec;
        }
    }
}
