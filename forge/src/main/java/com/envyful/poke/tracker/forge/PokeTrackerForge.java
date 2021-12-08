package com.envyful.poke.tracker.forge;

import com.envyful.api.config.yaml.YamlConfigFactory;
import com.envyful.api.forge.command.ForgeCommandFactory;
import com.envyful.api.forge.gui.factory.ForgeGuiFactory;
import com.envyful.api.forge.player.ForgePlayerManager;
import com.envyful.api.gui.factory.GuiFactory;
import com.envyful.poke.tracker.forge.command.PokeTrackerCommand;
import com.envyful.poke.tracker.forge.config.PokeTrackerConfig;
import com.envyful.poke.tracker.forge.config.PokeTrackerGui;
import com.envyful.poke.tracker.forge.config.PokeTrackerLocale;
import com.envyful.poke.tracker.forge.tracker.PokeTrackerFactory;
import com.envyful.poke.tracker.forge.tracker.listener.PokemonCatchListener;
import com.envyful.poke.tracker.forge.tracker.listener.PokemonSpawnListener;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

import java.io.IOException;

@Mod(
        modid = PokeTrackerForge.MOD_ID,
        name = "PokeTracker Forge",
        version = PokeTrackerForge.VERSION,
        acceptableRemoteVersions = "*",
        updateJSON = "https://ogn.pixelmonmod.com/update/sm-pk/update.json"
)
public class PokeTrackerForge {

    protected static final String MOD_ID = "poketracker";
    protected static final String VERSION = "0.3.3";

    @Mod.Instance(MOD_ID)
    private static PokeTrackerForge instance;

    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();
    private ForgePlayerManager playerManager = new ForgePlayerManager();

    private PokeTrackerConfig config;
    private PokeTrackerLocale locale;
    private PokeTrackerGui gui;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        PokeTrackerFactory.load();

        GuiFactory.setPlatformFactory(new ForgeGuiFactory());

        this.reloadConfig();
    }

    public void reloadConfig() {
        try {
            this.config = YamlConfigFactory.getInstance(PokeTrackerConfig.class);
            this.locale = YamlConfigFactory.getInstance(PokeTrackerLocale.class);
            this.gui = YamlConfigFactory.getInstance(PokeTrackerGui.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        new PokemonSpawnListener();
        new PokemonCatchListener();

        this.commandFactory.registerCommand(event.getServer(), new PokeTrackerCommand());
    }

    @Mod.EventHandler
    public void onServerStop(FMLServerStoppingEvent event) {
        PokeTrackerFactory.save();
    }

    public static PokeTrackerForge getInstance() {
        return instance;
    }

    public PokeTrackerConfig getConfig() {
        return this.config;
    }

    public PokeTrackerLocale getLocale() {
        return this.locale;
    }

    public PokeTrackerGui getGui() {
        return this.gui;
    }

    public ForgePlayerManager getPlayerManager() {
        return this.playerManager;
    }
}
