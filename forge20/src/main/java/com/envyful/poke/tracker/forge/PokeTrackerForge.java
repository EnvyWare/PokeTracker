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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod(PokeTrackerForge.MOD_ID)
public class PokeTrackerForge {

    protected static final String MOD_ID = "poketracker";
    protected static final String VERSION = "1.0.5";

    private static PokeTrackerForge instance;

    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();
    private ForgePlayerManager playerManager = new ForgePlayerManager();

    private PokeTrackerConfig config;
    private PokeTrackerLocale locale;
    private PokeTrackerGui gui;

    public PokeTrackerForge() {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onInit(ServerStartingEvent event) {
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

    @SubscribeEvent
    public void onServerStart(RegisterCommandsEvent event) {
        new PokemonSpawnListener();
        new PokemonCatchListener();

        this.commandFactory.registerCommand(event.getDispatcher(), new PokeTrackerCommand());
    }

    @SubscribeEvent
    public void onServerStop(ServerStoppingEvent event) {
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
