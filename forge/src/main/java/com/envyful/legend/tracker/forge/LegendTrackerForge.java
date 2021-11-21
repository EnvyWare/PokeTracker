package com.envyful.legend.tracker.forge;

import com.envyful.api.config.yaml.YamlConfigFactory;
import com.envyful.api.forge.command.ForgeCommandFactory;
import com.envyful.api.forge.gui.factory.ForgeGuiFactory;
import com.envyful.api.forge.player.ForgePlayerManager;
import com.envyful.api.gui.factory.GuiFactory;
import com.envyful.legend.tracker.forge.command.LegendTrackerCommand;
import com.envyful.legend.tracker.forge.config.LegendTrackerConfig;
import com.envyful.legend.tracker.forge.config.LegendTrackerLocale;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.IOException;

@Mod(
        modid = LegendTrackerForge.MOD_ID,
        name = "LegendTracker Forge",
        version = LegendTrackerForge.VERSION,
        acceptableRemoteVersions = "*"
)
public class LegendTrackerForge {

    protected static final String MOD_ID = "legendtracker";
    protected static final String VERSION = "1.6.0";

    @Mod.Instance(MOD_ID)
    private static LegendTrackerForge instance;

    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();
    private ForgePlayerManager playerManager = new ForgePlayerManager();

    private LegendTrackerConfig config;
    private LegendTrackerLocale locale;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        GuiFactory.setPlatformFactory(new ForgeGuiFactory());

        this.reloadConfig();
    }

    private void reloadConfig() {
        try {
            this.config = YamlConfigFactory.getInstance(LegendTrackerConfig.class);
            this.locale = YamlConfigFactory.getInstance(LegendTrackerLocale.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        this.commandFactory.registerCommand(event.getServer(), new LegendTrackerCommand());
    }

    public static LegendTrackerForge getInstance() {
        return instance;
    }

    public LegendTrackerLocale getLocale() {
        return this.locale;
    }
}
